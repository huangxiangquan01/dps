package client;

import util.PropLoader;
import util.Revoker;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 删除代码
 * 
 * @author Administrator
 */
public class DeleteTool {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		System.out.println("=========================================");
		System.out.println("============ 正在删除计划相关代码  =======");
		System.out.println("=========================================");
		// 检查参数
		if (args.length < 1) {
			System.err.println("Usage: DeleteTool <propfilename>");
			System.err.println("the propfilename is placed in the 'plan' folder of the 'resource' folder");
			System.exit(-1);
			return;
		}
		// 加载通用配置文件
		try {
			PropLoader.load();
		} catch (FileNotFoundException e1) {
			System.err.println("配置文件common.properties 没有找到");
			System.exit(-1);
			return;
		} catch (IOException e2) {
			System.err.println("加载配置文件common.properties 出错");
			System.exit(-1);
			return;
		}

		// 加载生成计划配置文件
		String planName = args[0];
		try {
			PropLoader.loadPlan(planName);;
		} catch (FileNotFoundException e1) {
			System.err.println("生成计划配置文件 "+planName+".properties 没有找到");
			System.exit(-1);
			return;
		} catch (IOException e2) {
			System.err.println("生成计划配置文件 "+planName+".properties 没有找到");
			System.exit(-1);
			return;
		}

//		String errTbName = "";
		try {
			//先只生成一个。 多个的以后再优化
//			for (String tableName : args) {
//				errTbName = tableName;
				// 生成代码
				new Revoker().revoke();
//			}
		} catch (Exception e) {
			//TODO 后面要改成失败后自动删除
			System.err.println("生成 " + planName + " 配置文件指定的相关文件出错，请删除相关文件重新生成");
			e.printStackTrace();
			System.exit(-1);
			return;
		}
		Long spendTime = System.currentTimeMillis()-startTime;
		System.out.println("============ 生成成功,操作耗时"+spendTime+"毫秒  =======");

	}

}
