package jp.co.iccom.yamamoto_izumi.calculate_sales ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SalesCalculationAssignment {


	public static void main (String [] args) throws FileNotFoundException {
		HashMap<String, String> branch = new HashMap<String,String> () ;
		HashMap<String, String> commodity = new HashMap<String,String> () ;
		HashMap <String, Long> branchSales = new HashMap <String, Long> () ;
		HashMap <String, Long> commoditySales = new HashMap <String, Long> () ;

		if (args.length != 1) {
			System.out.println("予期せぬエラーが発生しました");
			return ;
		}


		if(! input(args[0] + File.separator + "branch.lst", "^\\d{3}$", "支店",  branchSales, branch)) {
			return ;
		}


		if(! input(args[0] + File.separator + "commodity.lst", "^\\w{8}$", "商品", commoditySales, commodity)) {
			return ;
		}

		if(calculation(args[0] + File.separator, "^\\d{8}.rcd$", branchSales, commoditySales)){

		} else {
			System.out.println("予期せぬエラーが発生しました");
			return ;
		}


		if(!output(args[0] + File.separator + "branch.out", branchSales, branch)){
			return;
		}


		if(!output(args[0] + File.separator + "commodity.out", commoditySales, commodity)){
			return;
		}
	}

	//メソッド分け

	//出力するメソッド
	public static boolean output (String fileNames,
			HashMap <String,Long> profitMap, HashMap<String,String> listMap){
		BufferedWriter bw = null ;
		try{
			File file = new File (fileNames) ;
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			List<Map.Entry<String, Long>> entries = new ArrayList<Map.Entry<String,Long>>(profitMap.entrySet());

			Collections.sort(entries, new Comparator<Map.Entry<String, Long>>() {
				@Override
				public int compare(
				Entry<String,Long> entry1, Entry<String,Long> entry2) {
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}

			});

			for (Entry<String,Long> e : entries ) {
				bw.write(e.getKey() + "," + listMap.get(e.getKey()) + "," + e.getValue());
				bw.newLine();
			}

		}
		catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false ;
		}
		finally {
			if ( bw != null ) {
				try {
					bw.close();
				}
				catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
					return false ;
				}
			}
		}
		return true ;
	}

	//入力するメソッド
	public static boolean input (String fileNames, String digits, String Name,
			HashMap<String,Long> profitMap, HashMap<String,String> listMap) throws FileNotFoundException {
		File file = new File(fileNames) ;

		if (! file.exists()){
			System.out.println(Name + "定義ファイルが存在しません");
			return false ;
		}
		BufferedReader br = null ;

		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String str ;
			while ((str = br.readLine ()) != null) {
				String [] object = str.split(",",-1 ) ;

				if (object.length != 2) { //要素数が[0][1]
					System.out.println(Name + "定義ファイルのフォーマットが不正です");
				}
				if (! object[0].matches (digits))  { //[0]がX桁
					System.out.println(Name + "定義ファイルのフォーマットが不正です");
				}

				listMap.put(object[0] , object[1]);
				profitMap.put(object[0], 0L) ;
			}

		} catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false ;
		} finally {
			if ( br != null ) {
				try {
					br.close();
				}
				catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
					return false ;
				}
			}
		}
		return true ;
	}


	//売上を集計するメソッド

	public static boolean calculation (String path, String digitsRcd,
			HashMap<String, Long> branchProfit, HashMap<String,Long>commodityProfit) throws FileNotFoundException{


	//集計rcdのみ
		File dir = new File (path) ;
		File files [] = dir.listFiles () ;

	//rcdリストの変数宣言
		ArrayList<File> array = new ArrayList<File>();

		for (int i =0 ; i < files.length ; i++) {

			String fileName = files[i].getName() ;

			if (fileName.matches(digitsRcd) ){

				if(files[i].isFile()) {
					array.add (files[i]) ;
				}
			}
		}


		for (int i = 0 ; i < array.size() ; i++) {

			int obtain  = Integer.parseInt(array.get(i).getName().substring(0,8));

			int difference = obtain - i ;// 連番チェックは外でやったほうが、プログラム上でrcdのみになった後なので負担が少ない

			if (difference != 1) {
				System.out.println("売上ファイル名が連番になっていません");
				return false;
			}

		}//rcdのfor終了


		for (int i = 0 ;i < array.size() ; i++) {
			String Str = array.get(i).toString() ;
			FileReader fr = new FileReader (Str) ;
			BufferedReader br =new BufferedReader (fr) ;
			ArrayList<String> eachRcd = new ArrayList<String>();

			try {
				while ((Str = br.readLine ()) != null) {
					eachRcd.add(Str);
				}

				if (eachRcd.size() != 3 ) {
					System.out.println(array.get(i).getName() + "のフォーマットが不正です");
				}

				long s = Long.parseLong(eachRcd.get(2));

				if (! branchProfit.containsKey(eachRcd.get(0)) ) {
					System.out.println(array.get(i).getName() + "の支店コードが不正です");

				}else {
					branchProfit.put(eachRcd.get(0), s + branchProfit.get(eachRcd.get(0)) ) ;

				}

				if (! commodityProfit.containsKey(eachRcd.get(1))) {
					System.out.println(array.get(i).getName() + "の商品コードが不正です");

				}else {
					commodityProfit.put(eachRcd.get(1), s + commodityProfit.get(eachRcd.get(1))) ;
				}

				if (s + branchProfit.get(eachRcd.get(0)) > 9999999999L) {
					System.out.println("合計金額が10桁を超えました");
				}


			}

			catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return false ;
			}

			finally {
				if (br != null ) {
					try {
						br.close() ;
					}
					catch (IOException e) {
						System.out.println("予期せぬエラーが発生しました");
						return false ;
					}
				}
			}
		}
		return true;
	}
}
