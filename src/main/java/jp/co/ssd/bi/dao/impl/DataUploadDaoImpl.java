package jp.co.ssd.bi.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ssd.bi.constant.UploadCommonConst;
import jp.co.ssd.bi.dao.DataUploadDao;
import jp.co.ssd.bi.util.DBUtil;

@Service
public class DataUploadDaoImpl implements DataUploadDao {
	@Autowired
	DBUtil dbutil;
	
	@Override
	public void dataUpload(String filetype, Map<String, List<String>> excelData) {
		// TODO Auto-generated method stub
		Connection conn = dbutil.getConn();
		try{
			conn.setAutoCommit(false);
			for(Map.Entry<String, List<String>> tmpData : excelData.entrySet()) {				
				String tmpKey = tmpData.getKey();
				List<String> tmpValue = tmpData.getValue();
				//sql文生成
				Map <String,String> sqlMap = getsql(tmpKey,tmpValue);
				//テーブルを更新
				dbutil.queryUpdate(conn, sqlMap.get(UploadCommonConst.DELETE));
				dbutil.queryUpdate(conn, sqlMap.get(UploadCommonConst.INSERT));	
			}
			conn.commit();
		}catch (Exception e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
	}
	
	
	/**
	 * sql文生成
	 *  
	 * @param tableName テーブルネーム
	 * @param upLoadData アップデータ情報
	 * @return sqlMap sql情報
	 */	
	private Map<String,String> getsql(String tableName,List<String> upLoadData){

		Map <String,String> sqlMap = new LinkedHashMap<String,String>(); 
		String insCloumSql = "";
		String insValueSql = "";
		String delSql = "";
		
		for(int i = 0;i <= upLoadData.size()-1 ; i++) {
			String dataTmp[] = upLoadData.get(i).split(",");			
			if(UploadCommonConst.案件管理一覧.equals(tableName)) {				
				if (i < Integer.parseInt(dataTmp[2])) {
					insCloumSql = insCloumSql + dataTmp[0] + ",";
				}
				if(UploadCommonConst.課名.equals(dataTmp[0]) || UploadCommonConst.ユニット名.equals(dataTmp[0]) || UploadCommonConst.案件名.equals(dataTmp[0])) {
					delSql = delSql + dataTmp[0] + "=" + "'" + dataTmp[1] + "'" + " and ";
				}
				insValueSql = insValueSql + getInsVal(dataTmp[1]) + ",";			
				if((i+1)%Integer.parseInt(dataTmp[2]) == 0) {
					delSql = delSql.substring(0, delSql.length() - 5) + ") or (";
					insValueSql = insValueSql.substring(0, insValueSql.length() - 1) + "),(";

				}
	
			}else {
				insCloumSql = insCloumSql + dataTmp[0] + ",";
				if(UploadCommonConst.課名.equals(dataTmp[0]) || UploadCommonConst.ユニット名.equals(dataTmp[0]) || UploadCommonConst.案件名.equals(dataTmp[0]) || UploadCommonConst.会社区分.equals(dataTmp[0])) {
					delSql = delSql + dataTmp[0] + "=" + "'" + dataTmp[1] + "'" + " and  ";
				}
					insValueSql = insValueSql + getInsVal(dataTmp[1]) + "  ,";
										
			}
		}
		sqlMap.put(UploadCommonConst.DELETE, "delete from " + tableName + " where " + "(" + delSql.substring(0, delSql.length() - 6) + ")");
		sqlMap.put(UploadCommonConst.INSERT, "insert into " + tableName + "(" + insCloumSql.substring(0, insCloumSql.length() - 1) + ")" + " values " + "(" + insValueSql.substring(0, insValueSql.length() - 3) + ")");
				
		return sqlMap;	
	}	
	
	/**
	 * insertSql文作成
	 *  
	 * @param insValueSql 編集元文字列
	 * @return String 編集後文字列
	 */	
	private String getInsVal(String insValueSql) {
		if(UploadCommonConst.空セル.endsWith(insValueSql)) {
			return UploadCommonConst.NULL;
		}else {
			if(isNumber(insValueSql)) {
				return insValueSql;
			}else {
				return "'" + insValueSql + "'";
			}
		}
	}
	
	/**
	 * 数字型判断
	 *  
	 * @param str 判断文字列
	 * @return boolean 数字：true 数字以外:false
	 */	
	private static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);

    }

}
