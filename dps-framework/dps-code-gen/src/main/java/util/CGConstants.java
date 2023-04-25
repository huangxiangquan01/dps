package util;

public class CGConstants {

	//ftl文件地址
	public static final String propLocation = "ftl";
	
	//配置文件名
	public static final String propFileName = "common.properties";
	//POJO类的生成路径
	public static final String POJOLocation = System.getProperty("user.dir")+"/Genapi/src/main/java/pojo";
	//Service接口类的生成路径
	public static final String ServiceLocation = System.getProperty("user.dir")+"/Genapi/src/main/java/services";
	//Service接口实现类的生成路径
	public static final String ServiceImplLocation = System.getProperty("user.dir")+"/GenServer/src/main/java/com/genService/serviceImpl";
	//Service接口实现类的生成路径
	public static final String ControllerLocation = System.getProperty("user.dir")+"/Genauth/src/main/java/com/genauth/app/controller";
	
	public static final String HtmlLocation = System.getProperty("user.dir")+"/Genauth/src/main/resources/static/views";
	//TODO 下一步准备优化代码结构
//	public static final String ServiceLocation = "../Genapi/src/main/java/"+PropLoader.getPlanProp("plan.moduleName")+"services";
}
