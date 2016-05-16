package jp.co.iccom.yamamoto_izumi.calculate_sales ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class SalesCalculationAssignment{
public static void main(String [] args){
	HashMap<String, String> branchNameMap = new HashMap<String,String> () ;
	HashMap<String, String> commodityNameMap = new HashMap<String,String> () ;
	HashMap <String, Long> branchSalesMap = new HashMap <String, Long> () ;
	HashMap <String, Long> commoditySalesMap = new HashMap <String, Long> () ;

	if (args.length!= 1) {
		System.out.println("予期せぬエラーが発生しました");
		return ;
	}
	if(!input(args[0] + File.separator + "branch.lst", "^\\d{3}$", "支店",  branchSalesMap, branchNameMap)) {
		return ;
	}
	if(!input(args[0] + File.separator + "commodity.lst", "^\\w{8}$", "商品", commoditySalesMap, commodityNameMap)) {
		return ;
	}
	if(!calculation(args[0] + File.separator, "^\\d{8}.rcd$", branchSalesMap, commoditySalesMap)){
		return ;
	}
	if(!output(args[0] + File.separator + "branch.out", branchSalesMap, branchNameMap)){
		return;
	}
	if(!output(args[0] + File.separator + "commodity.out", commoditySalesMap, commodityNameMap)){
		return;
	}
}

//メソッド分け
//出力するメソッド
public static boolean output(String fileNames, HashMap <String,Long> profitMap, HashMap<String,String> listMap){
	BufferedWriter bw = null ;
	try{
		File file = new File ( fileNames ) ;
		FileWriter fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		List<Map.Entry<String, Long>> entries = new ArrayList<Map.Entry<String,Long>>( profitMap.entrySet() );
		Collections.sort(entries, new Comparator<Map.Entry<String, Long>>() {
			@Override
			public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2) {
				return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
			}
		});
		for (Entry<String,Long> e : entries ) {
			bw.write(e.getKey() + "," + listMap.get(e.getKey()) + "," + e.getValue());
			bw.newLine();
		}
	}catch (IOException e) {
		System.out.println("予期せぬエラーが発生しました");
		return false ;
	}finally {
		if ( bw != null ) {
			try {
				bw.close();
			}
			catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return false ;
			}
		}
	}return true ;
}

//入力するメソッド
public static boolean input(String fileNames, String digits, String Name, HashMap<String,Long> profitMap, HashMap<String,String> listMap)  {
	File file = new File( fileNames ) ;
	if (!file.exists()){
		System.out.println( Name + "定義ファイルが存在しません");
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
				System.out.println( Name + "定義ファイルのフォーマットが不正です");
				return false ;
			}
			if (! object[0].matches ( digits ))  { //[0]がX桁
				System.out.println( Name + "定義ファイルのフォーマットが不正です");
				return false ;
			}
			listMap.put(object[0] , object[1]);
			profitMap.put(object[0], 0L) ;
		}

	}catch (IOException e) {
		System.out.println("予期せぬエラーが発生しました");
		return false ;
	}finally {
		if ( br != null ) {
			try {
				br.close();
			}
			catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return false ;
			}
		}
	}return true ;
}

//売上を集計するメソッド
public static boolean calculation(String path, String digitsRcd, HashMap<String, Long> branchProfit, HashMap<String,Long> commodityProfit){
	File dir = new File ( path ) ;
	File files [] = dir.listFiles () ;
	ArrayList<File> arrayList = new ArrayList<File>();
	for (int i =0 ; i < files.length ; i++) {
		String fileName = files[i].getName() ;
		if (fileName.matches( digitsRcd ) ){
			if(files[i].isFile()) {
				arrayList.add (files[i]) ;
			}
		}
	}
	for (int i = 0 ; i < arrayList.size() ; i++) {
		int get  = Integer.parseInt(arrayList.get(i).getName().substring(0,8));
		int difference = get - i ;// 連番チェックは外でやったほうが負担が少ない
		if (difference!= 1) {
			System.out.println("売上ファイル名が連番になっていません");
			return false ;
		}
	}//rcdのfor終了
	for (int i = 0 ;i < arrayList.size() ; i++) {
		BufferedReader br = null ;
		try {
			FileReader fr = new FileReader (arrayList.get(i).toString()) ;
			br = new BufferedReader (fr) ;
			ArrayList<String> onlyRcd = new ArrayList<String>();
			String Str ;
			while ((Str = br.readLine ()) != null) {
				onlyRcd.add(Str);
			}
			if (onlyRcd.size()!= 3 ) {
				System.out.println(arrayList.get(i).getName() + "のフォーマットが不正です");
				return false ;
			}
			long profit = Long.parseLong(onlyRcd.get(2));
			if (!branchProfit.containsKey(onlyRcd.get(0)) ) {
				System.out.println(arrayList.get(i).getName() + "の支店コードが不正です");
				return false ;
			}else {
				branchProfit.put(onlyRcd.get(0), profit + branchProfit.get(onlyRcd.get(0)) ) ;
			}
			if (!commodityProfit.containsKey(onlyRcd.get(1))) {
				System.out.println(arrayList.get(i).getName() + "の商品コードが不正です");
				return false ;
			}else {
				commodityProfit.put(onlyRcd.get(1), profit + commodityProfit.get(onlyRcd.get(1))) ;
			}
			if (profit + branchProfit.get(onlyRcd.get(0)) >= 9999999999L) {
				System.out.println("合計金額が10桁を超えました");
				return false ;
			}
			if (profit + commodityProfit.get(onlyRcd.get(1)) >= 9999999999L) {
				System.out.println("合計金額が10桁を超えました");
				return false ;
			}
		}catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false ;
		}finally {
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
	}return true;
}
}