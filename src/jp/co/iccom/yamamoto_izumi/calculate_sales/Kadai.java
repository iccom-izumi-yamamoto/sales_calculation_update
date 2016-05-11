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

public class Kadai {


public static void main (String [] args) throws FileNotFoundException {
	HashMap<String, String> branch = new HashMap<String,String> () ;
	HashMap<String, String> commodity = new HashMap<String,String> () ;
	HashMap <String, Long> branchSales = new HashMap <String, Long> () ;
	HashMap <String, Long> commoditySales = new HashMap <String, Long> () ;

//支店定義の読込み
	File branchLst = new File (args[0]+ File.separator + "branch.lst") ;
	FileReader frBranch = new FileReader (branchLst) ;
	BufferedReader brBranch = new BufferedReader (frBranch) ;

	try {
		String str ;
		while ((str = brBranch.readLine ()) != null) {
			String [] bran = str.split(",",-1 ) ;

			for (int i= 0 ; i < bran.length ; i++) {

				if (bran.length != 2) {
					System.out.println("支店定義ファイルのフォーマットが不正です");
					return;
				}

				if (! bran[0].matches ("^\\d{3}$") ) {
					System.out.println("支店定義ファイルのフォーマットが不正です");
					return ;
				}
			}

			branch.put(bran[0] , bran[1]);
			branchSales.put(bran[0], 0L) ;
		}

	}
	catch (IOException e) {
		System.out.println("支店定義ファイルが存在しません");
		return ;
	}
	finally {
		try {
			brBranch.close();
		}
		catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		}
	}

	System.out.println(branch.entrySet());


	//商品定義の読込み
	File commodityLst = new File (args[0] + File.separator + "commodity.lst") ;
	FileReader frCommodity = new FileReader (commodityLst) ;
	BufferedReader brCommodity = new BufferedReader (frCommodity) ;

	try {

		String str ;

			while ((str = brCommodity.readLine ()) != null) {
				String [] com = str.split("," , -1) ;

				for (int i= 0 ; i < com.length ; i++) {

					if (com.length != 2) {
						System.out.println("商品定義ファイルのフォーマットが不正です");
						return;
					}

					if (! com[0].matches ("\\w{8}$"))  {
						System.out.println("商品定義ファイルのフォーマットが不正です");
						return ;
					}
				}
				commodity.put(com[0] , com[1]);
				commoditySales.put(com[0], 0L);
			}

	}
	catch (IOException e) {
		System.out.println("商品定義ファイルが存在しません");
		return ;
	}
	finally {
		try {
			brCommodity.close();
		}
		catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		}
	}
	System.out.println(commodity.entrySet());


 //集計rcdのみ
	File dir = new File (args[0] + File.separator) ;
	File files [] = dir.listFiles () ;

//rcdリストの変数宣言
	ArrayList<File> rcdList = new ArrayList<File>();

	for (int i =0 ; i < files.length ; i++) {
		if (files [i].toString().endsWith (".rcd") ) {
//			System.out.println(files [i].getName().substring(0,8));//rcd無しの数値

			int j = Integer.parseInt(files[i].getName().substring(0,8));
//				System.out.println(j);//数値に変換

			rcdList.add (files[i]) ;
//				System.out.println(files[i]);

			int k = j-i ;// 連番チェックは外でやったほうが、プログラム上でrcdのみになった後なので負担が少ない
//				System.out.println("i : " + i + "  j : " + j + "  k : " + k);

			if (k != 1) {
				System.out.println("売上ファイル名が連番になっていません");
				return ;
			}
		}
	}//rcdのfor終了

	try {
		for (int f = 0 ; f < rcdList.size() ; f++) {
			String Rcd = rcdList.get(f).toString() ;
			FileReader fr = new FileReader (Rcd) ;
			BufferedReader br =new BufferedReader (fr) ;
			ArrayList<String> eachRcd = new ArrayList<String>();

				while ((Rcd = br.readLine ()) != null) {
					System.out.println(Rcd);//Rcdファイルの中身抽出
					eachRcd.add(Rcd);
				}br.close();

			long s = Long.parseLong(eachRcd.get(2));	//rcdListをString型に変換してからLに振り返る

			branchSales.put(eachRcd.get(0), s + branchSales.get(eachRcd.get(0)) ) ;
			commoditySales.put(eachRcd.get(1), s + commoditySales.get(eachRcd.get(1))) ;


				if (s + branchSales.get(eachRcd.get(0)) > 999999999) {
					System.out.println("合計金額が10桁を超えました");
					return ;
				}


				if (! branchSales.containsKey(eachRcd.get(0)) ) {
					System.out.println(eachRcd.get(0) + "の支店コードが不正です");
					return ;
				}

				if (! commoditySales.containsKey(eachRcd.get(1))) {
					System.out.println(eachRcd.get(1) + "の商品コードが不正です");
					return ;
				}


				if (eachRcd.size() != 3 ) {
					System.out.println(eachRcd.get(f) + "のフォーマットが不正です");
					return ;
				}


		}

	System.out.println(branchSales);
	System.out.println(commoditySales);

	}
	catch (IOException e) {
		System.out.println("予期せぬエラーが発生しました");
		return;
	}


	File branchOut = new File (args[0] + File.separator + "branch.out" ) ;
	BufferedWriter branchWriter = null ;
		try {
			branchOut.createNewFile() ;
			FileWriter fw = new FileWriter (branchOut) ;
			BufferedWriter bw = new BufferedWriter (fw) ;
			List<Map.Entry<String, Long>> branchEntries = new ArrayList<Map.Entry<String,Long>>(branchSales.entrySet());

			Collections.sort(branchEntries, new Comparator<Map.Entry<String,Long>>() {

				@Override
				public int compare(
				Entry<String,Long> entry1, Entry<String,Long> entry2) {
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}

			});

			for (Entry<String, Long> b : branchEntries) {
				bw.write(b.getKey() + "," + branch.get(b.getKey()) + "," + b.getValue());
				bw.newLine();
			}

		}
		catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		}

		finally {
			if (branchWriter != null ) {
				try {
					branchWriter.close() ;
				}
				catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
				}
			}
		}


	File commodityOut = new File (args[0] + File.separator + "commodity.out") ;
	BufferedWriter commodityWriter = null ;
		try {
			commodityOut.createNewFile() ;
			FileWriter fw = new FileWriter (commodityOut) ;
			BufferedWriter bw = new BufferedWriter (fw) ;

			List<Map.Entry<String, Long>> commodityEntries = new ArrayList<Map.Entry<String,Long>>(commoditySales.entrySet());

			Collections.sort(commodityEntries, new Comparator<Map.Entry<String, Long>>() {

				@Override
				public int compare(
				Entry<String,Long> entry1, Entry<String,Long> entry2) {
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}

			});


			for (Entry<String,Long> c : commodityEntries ) {
				bw.write(c.getKey() + "," + commodity.get(c.getKey()) + "," + c.getValue());
				bw.newLine();
			}

		}
		catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		}
		finally {
			if ( commodityWriter != null ) {
				try {
					commodityWriter.close();
				}
				catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
				}
			}
		}
	}
}