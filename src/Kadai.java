import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Kadai {

	//支店定義の読込み
	public static void main (String [] args) {
		try {
			File file = new File (args[0] + "\\branch.lst") ;
			FileReader fr = new FileReader (file) ;
			BufferedReader br = new BufferedReader (fr) ;
			String str ;
			while ((str = br.readLine ()) != null) {
				String [] bran = str.split("," ) ;

				if (bran[0].length() != 3 ) {
				System.out.println("支店定義ファイルのフォーマットが不正です");
				}

				// System.out.println(str);
				HashMap<String, String> branch = new HashMap<String,String> () ;
				branch.put(bran[0] , bran[1]);
				System.out.println(branch.entrySet());

			}
			br.close () ;
		} catch (IOException e) {
			System.out.println("支店定義ファイルが存在しません");
			return ;
	}



	//商品定義の読込み
		try {
			File file = new File (args[0] + "\\commodity.lst") ;
			FileReader fr = new FileReader (file) ;
			BufferedReader br = new BufferedReader (fr) ;
			String str ;
			while ((str = br.readLine ()) != null) {
				String [] com = str.split(",") ;

				if (com[0].length() != 8 ) {
				System.out.println("商品定義ファイルのフォーマットが不正です");
				}

				HashMap<String, String> commodity = new HashMap<String,String> () ;
				commodity.put(com[0] , com[1]);
				System.out.println(commodity.entrySet());

			}
			br.close () ;
		} catch (IOException e) {
			System.out.println("商品定義ファイルが存在しません");
			return ;
		}

	//集計 rcd含んで8桁
//		args [0] でCドライブの課題ファイルを指定その中のrcdファイルが対象
//		if (file.contains (".rcd") && file.length == 12桁?) ;
//		どうやって連番じゃないのを認識するか？
//		これで出来る！　^[a-zA-Z0-9]{8}.rcd



	}
}