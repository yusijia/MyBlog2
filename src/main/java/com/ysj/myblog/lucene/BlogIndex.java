package com.ysj.myblog.lucene;

import com.ysj.myblog.entity.Blog;
import com.ysj.myblog.util.DateUtil;
import com.ysj.myblog.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * 创建博客索引的类
 * @author ysj
 *
 */
public class BlogIndex {

	// 索引存放的目录
	private Directory dir;
	
	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter() throws Exception{
		// 生成的索引写到这个目录下
		dir = FSDirectory.open(Paths.get("D://lucene-index"));
		// 中文分词器
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		// 配置中文分词器
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(dir, indexWriterConfig);
		return writer;
	}
	
	/**
	 * 后台添加博客后进行添加索引操作
	 * 前台lucene搜索时显示博客标题(链接到该博客,所以要取该博客的id)，发布日期，无html标签的博客内容
	 * @param blog Blog实例
	 * @throws Exception
	 */
	public void addIndex(Blog blog) throws Exception{
		IndexWriter writer = getWriter();
		Document doc = new Document();
		// 下面这些信息要存起来以后前台lucene搜索时要用，所以Field.Store.YES
		// 注意StringField不会分词, 查找的时候不需要分析id
		doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
		// 查找的时候对标题也需要分词所以不用StringField
		doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
		// 发布日期也不需要进行分词操作，所以用StringField
		doc.add(new StringField("releaseDateStr",
								DateUtil.formatDate(new Date(), "yyy-MM-dd"),
								Field.Store.YES));
		// 对博客内容(html标签)进行分词，用TextField, 注意contentNoTag没有存到数据库里
		doc.add(new TextField("contentNoTag",blog.getContentNoTag(),Field.Store.YES));
		writer.addDocument(doc);
		writer.close();
	}
	
	/**
	 * 更新指定博客的索引
	 * @param blog Blog实例
	 * @throws Exception
	 */
	public void updateIndex(Blog blog)throws Exception{
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id",String.valueOf(blog.getId()),Field.Store.YES));
		doc.add(new TextField("title",blog.getTitle(),Field.Store.YES));
		doc.add(new StringField("releaseDateStr",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("contentNoTag",blog.getContentNoTag(),Field.Store.YES));
		writer.updateDocument(new Term("id",String.valueOf(blog.getId())), doc);
		writer.close();
	}
	
	/**
	 * 删除指定博客的索引
	 * @param blogId 博客ID
	 * @throws Exception
	 */
	public void deleteIndex(String blogId)throws Exception{
		IndexWriter writer = getWriter();
		writer.deleteDocuments(new Term("id",blogId));
		writer.forceMergeDeletes(); // 强制删除
		writer.commit();
		writer.close();
	}
	
	/**
	 * 查询博客信息
	 * @param q 查询关键字
	 * @return 返回列表，存放了查询出的博客
	 * @throws Exception
	 */
	public List<Blog> searchBlog(String q)throws Exception{
		// 从这个目录下获取索引
		dir = FSDirectory.open(Paths.get("D://lucene-index"));
		// 获取IndexReader
		IndexReader reader = DirectoryReader.open(dir);
		// 获取IndexSearch
		IndexSearcher is = new IndexSearcher(reader);
		// 多条件查询配置
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		
		// 拼接查询条件
		// 在title和content里查询
		QueryParser parser = new QueryParser("title", analyzer);
		Query query = parser.parse(q);
		QueryParser parser2 = new QueryParser("contentNoTag", analyzer);
		Query query2 = parser2.parse(q);
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
		
		// 返回查询出的前100条数据
		TopDocs hits = is.search(booleanQuery.build(), 100);
		// 计算title得分高的排在前面(得分主要以title得分为主)
		QueryScorer scorer = new QueryScorer(query);

		// 配置要查找的代码片段高亮方案
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		// 高亮内容设置标签样式
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		
		// 遍历得分最佳的片段加入blogList
		List<Blog> blogList = new LinkedList<Blog>();
		for(ScoreDoc scoreDoc : hits.scoreDocs){
			Document doc = is.doc(scoreDoc.doc);
			// 添加要获取的blog信息到Blog对象里
			Blog blog = new Blog();
			blog.setId(Integer.parseInt(doc.get("id")));
			blog.setReleaseDateStr(doc.get("releaseDateStr"));
			// 将title和content里的像'<','>'...这种特殊字符,标签进行转义
			String title = StringEscapeUtils.escapeHtml(doc.get("title"));
			String content = StringEscapeUtils.escapeHtml(doc.get("contentNoTag"));
			if(title != null){
				// 处理下title
				// 解析title
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
				// 得分比较高的代码片段代码高亮处理
				String hTitle = highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(hTitle)){// 如果没找到比较符合的title
					blog.setTitle(title);
				}else{
					blog.setTitle(hTitle);
				}
			}
			
			if(content!=null){
				TokenStream tokenStream = analyzer.tokenStream("contentNoTag", new StringReader(content));
				String hContent = highlighter.getBestFragment(tokenStream, content);
				if(StringUtil.isEmpty(hContent)){
					if(content.length() <= 200){// 如果文章内容比较少
						blog.setContent(content);
					}else{
						blog.setContent(content.substring(0, 200));	
					}
				}else{
					blog.setContent(hContent);
				}
			}
			blogList.add(blog);
		}
		return blogList;
	}
	
}
