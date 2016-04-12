package com.example.pdfreport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText et1,et2,et3,et4;
	Button Save, et5;
	String edit1,edit2,edit3,edit4,text;
	PdfPTable table = new PdfPTable(2);
	PdfPCell cell1, cell2,cell3,cell4, cell5,cell6,cell7, cell8,cell9,cell10,cell11,cell12,cell13,cell14;
	File cacheDir;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		  if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"Code Analyser");
	        
	     
	        	
	        else 
	            cacheDir=context.getCacheDir();
	        if(!cacheDir.exists())
	            cacheDir.mkdirs();

        et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		et3=(EditText)findViewById(R.id.editText3);
		et4=(EditText)findViewById(R.id.editText4);
        et5=(Button)findViewById(R.id.button1);


        Save=(Button)findViewById(R.id.button1);
		Save.setOnClickListener(reportClickListener);
	}
	OnClickListener reportClickListener= new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

            edit1=et1.getText().toString();
			edit2=et2.getText().toString();
			edit3=et3.getText().toString();
			edit4=et4.getText().toString();
            text=et5.getText().toString();

            String FILE = Environment.getExternalStorageDirectory().toString() + "/Code Analyser/" + "report.pdf";

			// Create New Blank Document
			Document document = new Document(PageSize.A4);

			// Create Pdf Writer for Writting into New Created Document
			try {
				PdfWriter.getInstance(document, new FileOutputStream(FILE));
				// Open Document for Writting into document
				document.open();
				// User Define Method
				addTitlePage(document);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Close Document after writting all content
			document.close();
			
			Toast.makeText(MainActivity.this, "PDF File is Created."+FILE, Toast.LENGTH_LONG).show();
		}
	};
	// Set PDF document Properties	
		public void addTitlePage(Document document) throws DocumentException 
		{
			// Font Style for Document
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD| Font.UNDERLINE, BaseColor.GRAY);
			Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

			// Start New Paragraph
			Paragraph prHead = new Paragraph();
			// Set Font in this Paragraph
			prHead.setFont(titleFont);
			// Add item into Paragraph
			prHead.add("Code Analyzer\n");
			//prHead.add("\n");
			prHead.setAlignment(Element.ALIGN_CENTER);

			Paragraph cat = new Paragraph();
			cat.setFont(catFont);
			cat.add("\n");
			cat.add("Report\n");
			cat.add("\n");
			cat.setAlignment(Element.ALIGN_CENTER);

			// Add all above details into Document
			document.add(prHead);
			document.add(cat);
			document.add(table);

			/* Header arrays*/
			table = new PdfPTable(2);
			cell1 = new PdfPCell(new Phrase("Category"));
			cell2 = new PdfPCell(new Phrase("Values"));
			cell1.setVerticalAlignment(Element.ALIGN_LEFT);
			cell2.setVerticalAlignment(Element.ALIGN_LEFT);

			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setPadding(5);
			
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setPadding(5);
			
			cell1.setBackgroundColor(BaseColor.GRAY);
			cell2.setBackgroundColor(BaseColor.GRAY);

			/*Table arrays*/
			cell3 = new PdfPCell(new Phrase("Name"));
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3.setBorder(Rectangle.NO_BORDER);
			cell3.setPadding(5);
			
			cell4 = new PdfPCell(new Phrase(edit1));
			cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell4.setBorder(Rectangle.NO_BORDER);
			cell4.setPadding(5);

			cell5 = new PdfPCell(new Phrase("Mobile Number"));
			cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell5.setBorder(Rectangle.NO_BORDER);
			cell5.setPadding(5);
			
			cell6 = new PdfPCell(new Phrase(edit2));
			cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell6.setBorder(Rectangle.NO_BORDER);
			cell6.setPadding(5);
			
			cell7 = new PdfPCell(new Phrase("Mail Id"));
			cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell7.setBorder(Rectangle.NO_BORDER);
			cell7.setPadding(5);
			
			cell8 = new PdfPCell(new Phrase(edit3));
			cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell8.setBorder(Rectangle.NO_BORDER);
			cell8.setPadding(5);
			
			cell9 = new PdfPCell(new Phrase("City"));
			cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell9.setBorder(Rectangle.NO_BORDER);
			cell9.setPadding(5);
			
			cell10 = new PdfPCell(new Phrase(edit4));
			cell10.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell10.setBorder(Rectangle.NO_BORDER);
			cell10.setPadding(5);

            cell11 = new PdfPCell(new Phrase("Names"));
            cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell11.setBorder(Rectangle.NO_BORDER);
            cell11.setPadding(5);

            cell12 = new PdfPCell(new Phrase(text));
            cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell12.setBorder(Rectangle.NO_BORDER);
            cell12.setPadding(5);

            cell13 = new PdfPCell(new Phrase("Spinners"));
            cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell13.setBorder(Rectangle.NO_BORDER);
            cell13.setPadding(5);

            cell14 = new PdfPCell(new Phrase(text));
            cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell14.setBorder(Rectangle.NO_BORDER);
            cell14.setPadding(5);

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.addCell(cell10);
            table.addCell(cell11);
            table.addCell(cell12);
            table.addCell(cell13);
            table.addCell(cell14);
            // add table into document
			document.add(table);

			// Create new Page in PDF
			document.newPage();
			//Toast.makeText(this, "PDF File is Created.", Toast.LENGTH_LONG).show();
		}

    public void buttonOnClick(View v) {

        Button button=(Button) v;
        startActivity(new Intent(getApplicationContext(), MainActivity2Activity.class));
    }}