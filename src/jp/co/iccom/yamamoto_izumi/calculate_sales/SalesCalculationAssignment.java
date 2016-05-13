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
		System.out.println("予期せぬエラーが発生しました1");
		return ;
	}

//支店定義の読込み

	if(! input(args[0] + File.separator + "branch.lst", "^\\d{3}$", "支店",  branchSales, branch)) {
		System.out.println("予期せぬエラーが発生しました");
		return ;
	}

//	File branchLst = new File (args[0]+ File.separator + "branch.lst") ;
//
//	if (! branchLst.exists()){
//	    System.out.println("支店定義ファイルが存在しません");
//	    return ;
//	}
//
//	FileReader frBranch = new FileReader (branchLst) ;
//	BufferedReader brBranch = new BufferedReader (frBranch) ;
//
//	try {
//		String str ;
//		while ((str = brBranch.readLine ()) != null) {
//			String [] bran = str.split(",",-1 ) ;
//
//			if (bran.length != 2) {
//				System.out.println("支店定義ファイルのフォーマットが不正です");
//				return;
//			}
//
//			if (! bran[0].matches ("^\\d{3}$") ) {
//				System.out.println("支店定義ファイルのフォーマットが不正です");
//				return ;
//			}
//
//		branch.put(bran[0] , bran[1]);
//		branchSales.put(bran[0], 0L) ;
//		}
//
//	}
//	catch (IOException e) {
//		System.out.println("予期せぬエラーが発生しました");
//		return ;
//	}
//	finally {
//		try {
//			brBranch.close();
//		}
//		catch (IOException e) {
//			System.out.println("予期せぬエラーが発生しました");
//			return ;
//		}
//	}



	//商品定義の読込み

	if(! input(args[0] + File.separator + "commodity.lst", "^\\w{8}$", "商品", commoditySales, commodity)) {
		System.out.println("予期せぬエラーが発生しました");
		return ;
	}
//
//	File commodityLst = new File (args[0] + File.separator + "commodity.lst") ;
//
//	if (! commodityLst.exists()){
//	    System.out.println("商品定義ファイルが存在しません");
//	    return ;
//	}
//
//	FileReader frCommodity = new FileReader (commodityLst) ;
//	BufferedReader brCommodity = new BufferedReader (frCommodity) ;
//
//	try {
//		String str ;
//
//		while ((str = brCommodity.readLine ()) != null) {
//			String [] com = str.split("," , -1) ;
//
//			if (com.length != 2) {
//				System.out.println("商品定義ファイルのフォーマットが不正です");
//				return;
//			}
//
//			if (! com[0].matches ("\\w{8}$"))  {
//				System.out.println("商品定義ファイルのフォーマットが不正です");
//				return ;
//			}
//
//			commodity.put(com[0] , com[1]);
//			commoditySales.put(com[0], 0L);
//		}
//
//	}
//	catch (IOException e) {
//		System.out.println("予期せぬエラーが発生しました");
//		return ;
//	}
//	finally {
//		try {
//			brCommodity.close();
//		}
//		catch (IOException e) {
//			System.out.println("予期せぬエラーが発生しました");
//			return ;
//		}
//	}

	if(!calculation(args[0] + File.separator, "^\\d{8}.rcd$", branchSales, commoditySales)){

	} else {
		System.out.println("予期せぬエラーが発生しました");
		return ;
	}

// //集計rcdのみ
//	File dir = new File (args[0] + File.separator) ;
//	File files [] = dir.listFiles () ;
//
////rcdリストの変数宣言
//	ArrayList<File> rcdList = new ArrayList<File>();
//
//	for (int i =0 ; i < files.length ; i++) {
//
//		String fileName = files[i].getName() ;
//
//		if (fileName.matches("^\\d{8}.rcd$") ){
//
//			if(files[i].isFile()) {
//				rcdList.add (files[i]) ;
//			}
//		}
//	}
//
//
//	for (int i = 0 ; i < rcdList.size() ; i++) {
//
//		int obtain  = Integer.parseInt(rcdList.get(i).getName().substring(0,8));
//
//		int difference = obtain - i ;// 連番チェックは外でやったほうが、プログラム上でrcdのみになった後なので負担が少ない
//
//		if (difference != 1) {
//			System.out.println("売上ファイル名が連番になっていません");
//			return ;
//		}
//
//	}//rcdのfor終了
//
//
//	for (int f = 0 ; f < rcdList.size() ; f++) {
//		String Rcd = rcdList.get(f).toString() ;
//		FileReader frRcd = new FileReader (Rcd) ;
//		BufferedReader brRcd =new BufferedReader (frRcd) ;
//		ArrayList<String> eachRcd = new ArrayList<String>();
//
//		try {
//			while ((Rcd = brRcd.readLine ()) != null) {
//				eachRcd.add(Rcd);
//			}
//
//			if (eachRcd.size() != 3 ) {
//				System.out.println(rcdList.get(f).getName() + "のフォーマットが不正です");
//				return ;
//			}
//
//			long s = Long.parseLong(eachRcd.get(2));
//
//			if (! branchSales.containsKey(eachRcd.get(0)) ) {
//				System.out.println(rcdList.get(f).getName() + "の支店コードが不正です");
//				return ;
//
//			}else {
//				branchSales.put(eachRcd.get(0), s + branchSales.get(eachRcd.get(0)) ) ;
//
//			}
//
//			if (! commoditySales.containsKey(eachRcd.get(1))) {
//				System.out.println(rcdList.get(f).getName() + "の商品コードが不正です");
//				return ;
//
//			}else {
//				commoditySales.put(eachRcd.get(1), s + commoditySales.get(eachRcd.get(1))) ;
//			}
//
//			if (s + branchSales.get(eachRcd.get(0)) > 9999999999L) {
//				System.out.println("合計金額が10桁を超えました");
//				return ;
//			}
//
//
//		}
//
//		catch (IOException e) {
//			System.out.println("予期せぬエラーが発生しました");
//			return;
//		}
//
//		finally {
//			if (brRcd != null ) {
//				try {
//					brRcd.close() ;
//				}
//				catch (IOException e) {
//					System.out.println("予期せぬエラーが発生しました");
//					return ;
//				}
//			}
//		}
//	}

	if(!output(args[0] + File.separator + "branch.out", branchSales, branch)){
		System.out.println("予期せぬエラーが発生しました3");
		return;
	}
//
//	File branchOut = new File (args[0] + File.separator + "branch.out" ) ;
//	BufferedWriter branchWriter = null ;
//	try {
//		branchWriter = new BufferedWriter(new FileWriter(branchOut)) ;
//		List<Map.Entry<String, Long>> branchEntries = new ArrayList<Map.Entry<String,Long>>(branchSales.entrySet());
//
//		Collections.sort(branchEntries, new Comparator<Map.Entry<String,Long>>() {
//			@Override
//			public int compare(
//			Entry<String,Long> entry1, Entry<String,Long> entry2) {
//				return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
//			}
//
//		});
//
//		for (Entry<String, Long> b : branchEntries) {
//			branchWriter.write(b.getKey() + "," + branch.get(b.getKey()) + "," + b.getValue());
//			branchWriter.newLine();
//		}
//
//	}
//	catch (IOException e) {
//		System.out.println("予期せぬエラーが発生しました");
//		return ;
//	}
//
//	finally {
//		if (branchWriter != null ) {
//			try {
//				branchWriter.close() ;
//			}
//			catch (IOException e) {
//				System.out.println("予期せぬエラーが発生しました");
//				return ;
//			}
//		}
//	}


	if(!output(args[0] + File.separator + "commodity.out", commoditySales, commodity)){
		System.out.println("予期せぬエラーが発生しました");
		return;
	}

//
//	File commodityOut = new File (args[0] + File.separator + "commodity.out") ;
//	BufferedWriter commodityWriter = null ;
//	try {
//		commodityWriter = new BufferedWriter(new FileWriter(commodityOut)) ;
//		List<Map.Entry<String, Long>> commodityEntries = new ArrayList<Map.Entry<String,Long>>(commoditySales.entrySet());
//
//		Collections.sort(commodityEntries, new Comparator<Map.Entry<String, Long>>() {
//			@Override
//			public int compare(
//			Entry<String,Long> entry1, Entry<String,Long> entry2) {
//				return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
//			}
//
//		});
//
//
//		for (Entry<String,Long> c : commodityEntries ) {
//			commodityWriter.write(c.getKey() + "," + commodity.get(c.getKey()) + "," + c.getValue());
//			commodityWriter.newLine();
//		}
//	}
//	catch (IOException e) {
//		System.out.println("予期せぬエラーが発生しました");
//		return ;
//	}
//	finally {
//		if ( commodityWriter != null ) {
//			try {
//				commodityWriter.close();
//			}
//			catch (IOException e) {
//				System.out.println("予期せぬエラーが発生しました");
//				return ;
//			}
//		}
//	}
}






//メソッド分け

//出力するメソッド
public static boolean output (String fileNames, HashMap <String,Long> profitMap, HashMap<String,String> listMap){
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
		return true ;
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
}

//入力するメソッド
public static boolean input (String fileNames, String digits, String Name,
		HashMap<String,Long> profitMap, HashMap<String,String> listMap) throws FileNotFoundException {
	File file = new File(fileNames) ;

	if (! file.exists()){
		System.out.println(Name + "定義ファイルが存在しません");
		return false ;
	}

	FileReader fr = new FileReader(file) ;
	BufferedReader br = new BufferedReader(fr) ;

	try {
		String str ;
		while ((str = br.readLine ()) != null) {
			String [] object = str.split(",",-1 ) ;

			if (object.length != 2) { //要素数が[0][1][2]
				System.out.println(Name + "定義ファイルのフォーマットが不正です");
				return false ;
			}
			if (! object[0].matches (digits))  { //[0]がX桁
				System.out.println(Name + "定義ファイルのフォーマットが不正です");
				return false ;
			}

		listMap.put(object[0] , object[1]);
		profitMap.put(object[0], 0L) ;
	}
		return true ;
	}
	catch (IOException e) {
		System.out.println("予期せぬエラーが発生しました");
		return false ;
	}
	finally {
		try {
			br.close();
		}
		catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false ;
		}
	}
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
				return false ;
			}

			long s = Long.parseLong(eachRcd.get(2));

			if (! branchProfit.containsKey(eachRcd.get(0)) ) {
				System.out.println(array.get(i).getName() + "の支店コードが不正です");
				return false ;

			}else {
				branchProfit.put(eachRcd.get(0), s + branchProfit.get(eachRcd.get(0)) ) ;

			}

			if (! commodityProfit.containsKey(eachRcd.get(1))) {
				System.out.println(array.get(i).getName() + "の商品コードが不正です");
				return false ;

			}else {
				commodityProfit.put(eachRcd.get(1), s + commodityProfit.get(eachRcd.get(1))) ;
			}

			if (s + branchProfit.get(eachRcd.get(0)) > 9999999999L) {
				System.out.println("合計金額が10桁を超えました");
				return false ;
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
	return false;
}
}
