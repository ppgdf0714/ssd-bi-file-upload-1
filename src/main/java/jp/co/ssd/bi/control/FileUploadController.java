package jp.co.ssd.bi.control;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ssd.bi.service.FileUploadService;

@Controller
@Transactional
public class FileUploadController {
	@Autowired
	FileUploadService fileUploadService;
	@Value("${jp.co.sdd.bi.xmlname}")
	private String xmlname;
	/**
	 * 初期ページ表示
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}
	
	/**
     *　ファイルアップロード
     *
     * @param filetype ファイルタイプ
     * @param file アップロードファイル
     * @return 
     * @throws Exception
     */
	@RequestMapping("/importExcel")
    public String importExcel(@RequestParam(value = "filetype",required = false) String filetype,MultipartFile file) throws Exception{
		//XMLファイル読み込み	
		Map<String,List<String>> xmlData = fileUploadService.xmlLoad(filetype,xmlname);
		//excelファイル読み込み
		Map<String,List<String>> excelData = fileUploadService.getExcelData(filetype,file,xmlData);
		//テーブルを更新
		fileUploadService.dataUpload(filetype,excelData);
		return "ok";
    }
}
