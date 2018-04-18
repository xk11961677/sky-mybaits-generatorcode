package util;

import java.util.List;
import codeGenerate.CreateBean;
import codeGenerate.TableInfo;
import codeGenerate.def.CodeResourceUtil;

public class QueryTable {

	private static String url;
	private static String username;
	private static String passWord;
	private static String databaseName;

	public QueryTable() {
	}

	public static String getAllTables(){
		String allTableStr = "";
		try {
			CreateBean createBean = new CreateBean();
			createBean.setMysqlInfo(url, username, passWord,databaseName);
			List<TableInfo> tableList = createBean.getTablesInfo();
			for(int i=0;i<tableList.size();i++){
				TableInfo tableInfo = tableList.get(i);
				if(i==0){
					allTableStr += tableInfo.getTableName();
				}else{
					allTableStr += ","+tableInfo.getTableName();
				}
			}
			System.out.println(allTableStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allTableStr;
	}

	static {
		url = CodeResourceUtil.URL;
		username = CodeResourceUtil.USERNAME;
		passWord = CodeResourceUtil.PASSWORD;
		databaseName = CodeResourceUtil.DATABASE_NAME;
	}
}
