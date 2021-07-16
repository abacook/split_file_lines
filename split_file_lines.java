package gzip_split;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;



public class Gzip_split {
	
	public static void main(String[] args) {
		 String file = System.getProperty("file");
		 int lines = Integer.parseInt(System.getProperty("lines"));
		 if (lines != 0){gzipSplit(file,lines);}
		}
	
	public static void gzipSplit(String file, int lines){
		try{
		    int file_number = 0;
		    int write_lines = 0;
		    ArrayList<String> split_files=new ArrayList<String>(); 
		    
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							new GZIPInputStream(
									new FileInputStream(file))
		                     )
            );
            
			String fileFirstLine;
			fileFirstLine = reader.readLine();
			while (true) {
				// write file per lines
				if (fileFirstLine == null){
					break;
				}
				int i =0;
				String outputFile = file.substring(0,file.length()-3) + "." + String.format("%05d", file_number) + ".gz";
				BufferedOutputStream writer  =  new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outputFile)));  
				writer.write((fileFirstLine).getBytes());
				write_lines += 1;
				i += 1;
        
				String nextLine;
	            while(i< lines){
	            	nextLine = reader.readLine();
	            	if (nextLine != null){
	            		writer.write(("\n"+nextLine).getBytes());
	            		write_lines += 1;
	            		i += 1;
	            		}
	            	else{
	            		break;
	            		}
	            }
	            writer.close();
	       
	            file_number += 1;
	            split_files.add(outputFile);
	            fileFirstLine = reader.readLine();
	           
			}
			 
            reader.close();    
            System.out.println("SPLIT_FILES: " + listToString(split_files,";"));
            System.out.println("WRITE_LINES: " + write_lines);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		};
		
	}

	public static String listToString(ArrayList<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

}
