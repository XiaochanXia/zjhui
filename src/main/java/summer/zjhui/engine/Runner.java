package summer.zjhui.engine;

public class Runner {

	public static void main(String[] args) throws Throwable {
		String[] arguements = {
				"-classpath \"src\\features\\operate.feature\"",
				"--glue summer.zjhui.engine",
				"--monochrome",
				"--plugin pretty"
		};
		cucumber.api.cli.Main.main(arguements);

	}

}
