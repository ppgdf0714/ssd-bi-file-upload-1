package jp.co.ssd.bi.dao;

import java.util.List;
import java.util.Map;

public interface DataUploadDao {
	public void dataUpload(String filetype,Map<String,List<String>> excelData);
}
