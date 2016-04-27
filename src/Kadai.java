import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Kadai {

	//支店定義の読込み
	public static void main (String [] args) {
		HashMap<String, String> branch = new HashMap<String,String> () ;
		HashMap<String, String> commodity = new HashMap<String,String> () ;

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

				branch.put(bran[0] , bran[1]);

			}
			br.close () ;
		} catch (IOException e) {
			System.out.println("支店定義ファイルが存在しません");
			return ;
		}
		System.out.println(branch.entrySet());


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
				commodity.put(com[0] , com[1]);
			}
			br.close () ;
		}
		catch (IOException e) {
			System.out.println("商品定義ファイルが存在しません");
			return ;
		}
		System.out.println(commodity.entrySet());

 //集計rcdのみ
		File dir = new File (args[0]) ;
		File files [] = dir.listFiles () ;



		for (int i =0 ; i < files.length ; i++) {
//			System.out.println(i);
			if (files [i].toString().endsWith (".rcd") ) {
//			System.out.println(files[i].getName())
				System.out.println(files [i].getName().substring(0,8));//rcd無しの数値


				int j = Integer.parseInt(files[i].getName().substring(0,8));
	//			System.out.println(j);//数値に変換

				int k = j-i ;
				if (k != 1) {
					System.out.println("連番ではありません");
					return ;
	//			} else {
	//			System.out.println("連番ではありません");
				}

			}
//rcdをリストに格納することで、読込み・加算が限定できる
		}
			ArrayList<String> RcdFiles = new ArrayList<String> () ;
			RcdFiles.add("00000001.rcd") ;
			RcdFiles.add("00000002.rcd") ;
			RcdFiles.add("00000003.rcd") ;
			RcdFiles.add("00000004.rcd") ;


			//}
	}//ifの終わり

//	}
}