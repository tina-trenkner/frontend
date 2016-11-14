package jobs

import java.io.File

import app.LifecycleComponent
import common.{AkkaAsync, ExecutionContexts, JobScheduler, Logging}
import org.apache.commons.io.FileUtils
import play.api.inject.ApplicationLifecycle
import services.S3

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}


class CommercialLogsRepairJob(
  appLifecycle: ApplicationLifecycle,
  jobs: JobScheduler,
  akkaAsync: AkkaAsync)(implicit ec: ExecutionContext) extends LifecycleComponent with ExecutionContexts with Logging  {

  appLifecycle.addStopHook { () => Future {
    jobs.deschedule("CommercialLogsRepairJob")
  }}

  override def start(): Unit = {
    jobs.deschedule("CommercialLogsRepairJob")
    jobs.deschedule("CommercialLogsRepairJob")

//     5 minutes between each log write.
        jobs.scheduleEvery("CommercialLogRepairJob", 5.seconds) {
          println( "do this every 5 secs")
          getLogsFromS3(akkaAsync)
        }

  }

  private def getLogsFromS3 (akkaAsync: AkkaAsync):Future[Unit] = Future {

    akkaAsync.after1s{
//      val testString = "7845/imps/2016/11/14/02/data.csv.bz2"
      val testKey = "date=2016-10-05/2016-10-05 10-45"

      val testGet = S3CommercialLogs.get(testKey)

      println(testGet)
      //    val outputFile = new File("test.csv.bz2")
      //    outputFile.createNewFile()
      //    FileUtils.deleteQuietly(outputFile)
    }
  }
}


object S3CommercialLogs extends S3 {
  // A bucket owned by the Ophan team, it's not in Frontend. We have been given cross-account access.
//  override lazy val bucket = "ophan-raw-rubicon"
  override lazy val bucket = "ophan-raw-client-side-ad-metrics"
}
