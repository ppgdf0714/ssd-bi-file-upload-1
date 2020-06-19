package jp.co.ssd.bi.model;

public class MyException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String msg;

    public MyException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
