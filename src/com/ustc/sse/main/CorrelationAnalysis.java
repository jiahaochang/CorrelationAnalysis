package com.ustc.sse.main;

import com.ustc.sse.conf.PathConf;
import com.ustc.sse.util.FileUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Jiahao Zhang
 * @school University of Science and Technology of China
 * @mail jiahao_zhang@qq.com
 */

public class CorrelationAnalysis {
	
	public static class correlationAnalysisParameters{
		double alpha =	0.5;
		double beta =	0.6;
		int gamma =	2;
	}
	
	public enum parameters{
		alpha, beta, gamma;
	}
	
	private static void getParametersFromFile(correlationAnalysisParameters correlationAnalysisparameters,
			String parameterFile) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> paramLines = new ArrayList<String>();
		FileUtil.readLines(parameterFile, paramLines);
		
		for(String line : paramLines){
			String[] lineParts = line.split("\t");
			switch(parameters.valueOf(lineParts[0])){
			case alpha:
				correlationAnalysisparameters.alpha = Double.valueOf(lineParts[1]);
				break;
			case beta:
				correlationAnalysisparameters.beta = Double.valueOf(lineParts[1]);
				break;
			case gamma:
				correlationAnalysisparameters.gamma = Integer.valueOf(lineParts[1]);
				break;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		System.out.println("程序开始执行");
		
		Documents dt = new Documents();
		
		//读入一维向量T
		dt.readInputT(PathConf.InputTPath);
		//读入二维向量X
		dt.readInputX(PathConf.InputXPath);
		
		//获取单个参数alpha	beta gamma的值
		String parameterFile = PathConf.PARAMETERFILE;
		correlationAnalysisParameters parameters = new correlationAnalysisParameters();
		getParametersFromFile(parameters,parameterFile);
		
		dt.seekCombinationsAndCountM();
		dt.polymerization(parameters.alpha, parameters.beta, parameters.gamma);
		dt.outputResult(PathConf.OutputPath);
		
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		System.out.println("程序执行结束");
		
	}
	
}
