package utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;  //UI of the report
    public ExtentReports extent;  //populate common info on the report
	public ExtentTest test; //creating test case entries in the report and update status of the test methods
	
	String repName;
	
	  public void onStart(ITestContext testContext) 
	  { 
		  
		  
		  /*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		  Date dt=new Date(0);
		  String currentdatetimestamp=df.format(dt);
		  */
		  
		  
		  String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(0));
		  repName="Test-Report-" + timeStamp + ".html";
		  sparkReporter=new ExtentSparkReporter(".\\reports\\" + repName); // specify the location of the report
		  
		  sparkReporter.config().setDocumentTitle("Opencart Automation Report"); //Title of report
		  sparkReporter.config().setReportName("pencart Functional Testing");   //name of the report
		  sparkReporter.config().setTheme(Theme.DARK);
		  
		  extent=new ExtentReports();
		  extent.attachReporter(sparkReporter);
		  
		  extent.setSystemInfo("Application", "opencart");
		  extent.setSystemInfo("Module", "admin");
		  extent.setSystemInfo("Sub Module	", "Customers");
		  extent.setSystemInfo("User Name", System.getProperty("user.name"));
		  extent.setSystemInfo("Environment", "QA");
		  
		  String os=testContext.getCurrentXmlTest().getParameter("os");
		  extent.setSystemInfo("Operating System", os);
		  
		  String browser=testContext.getCurrentXmlTest().getParameter("browser");
		  extent.setSystemInfo("Browser", browser);
		  
		  List<String> includedgroups=testContext.getCurrentXmlTest().getExcludedGroups();
		  if(!includedgroups.isEmpty())
		  {
			  extent.setSystemInfo("Groups", includedgroups.toString());
		  }
		  
	  }
	  
	  public void onTestSuccess(ITestResult result) 
	  {
		  
		  test=extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		  test.assignCategory(result.getMethod().getGroups());   //to display groups in report
		  test.log(Status.PASS,result.getName()+"got Successfully executed");  //update status p/f/s
	  }	  
	  
	  public void onTestFailure(ITestResult result)
	  {
		  test=extent.createTest(result.getTestClass().getName());
		  test.assignCategory(result.getMethod().getGroups());
		  
		  test.log(Status.FAIL,result.getName()+"got failed");
		  test.log(Status.INFO, result.getThrowable().getMessage());
		  
		  try
		  {
			  String imgPath=new BaseClass().captureScreen(result.getName());
			  test.addScreenCaptureFromPath(imgPath);
		  }
		  catch (IOException e1) 
		  {
			e1.printStackTrace();
		}
		  
	  }
	  
	  public void onTestSkipped(ITestResult result)
	  {
		  test=extent.createTest(result.getTestClass().getName());
		  test.assignCategory(result.getMethod().getGroups());
		  
		  test.log(Status.SKIP,result.getName()+"got skipped");
		  test.log(Status.INFO, result.getThrowable().getMessage());
		   
	  }
	  
	  public void onFinish(ITestContext context)
	  {
		  extent.flush();
		  
		  String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		  File extentReport=new File(pathOfExtentReport);
		  
		  try
		  {
			  Desktop.getDesktop().browse(extentReport.toURI());
		  }   
		  
		  catch (Exception e) 
		  {
			e.printStackTrace();
		  }
		  
		  
		  /*
		  try
		  {
			  URL url= new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		  
		  //Create the email message
		  ImageHtmlEmail email=new ImageHtmlEmail();
		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
		  email.setHostName("smtp.googlemail.com");
		  email.setSmtpPort(465);
		  email.setAuthenticator(new DefaultAuthenticator("guna.bittu@gmail.com", "password"));
		  email.setSSLOnConnect(true);
		  email.setFrom("guna.bittu@gmail.com"); //sender
		  email.setSubject("Test Results");
		  email.setMsg("Please find Attached report....");
		  email.addTo("gunabittu0@gmail.com");   //Receiver
		  email.attach(url,"extent report","please check report");
		  email.send();  //send the email
		  }
		  catch (Exception e) 
		  {
			e.printStackTrace();
		}
		  */
		  
		  
		  
		  
		  
		  
	  }
	 
	  
	 }


