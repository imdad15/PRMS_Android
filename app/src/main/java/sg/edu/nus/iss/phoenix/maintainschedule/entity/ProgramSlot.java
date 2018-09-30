package sg.edu.nus.iss.phoenix.maintainschedule.entity;

public class ProgramSlot {
    private String radioProgramName;
    private String radioProgramPresenter;
    private String radioProgramProducer;
    private String radioProgramDuration;
    private String date;
    private String startTime;
    private String assignedBy;
    private int yearNum;
    private int weekNum;



    public ProgramSlot(String radioProgramName,String radioProgramPresenter,String radioProgramProducer,
             String radioProgramDuration,String date,String startTime,String assignedBy) {

       this.radioProgramName=radioProgramName;
        this.radioProgramPresenter=radioProgramPresenter;
        this.radioProgramProducer=radioProgramProducer;
        this.radioProgramDuration=radioProgramDuration;
        this.date=date;
        this.startTime=startTime;
        this.assignedBy=assignedBy;

    }

    public String getRadioProgramName() {
        return radioProgramName;
    }

    public String getProducerName() {
        return radioProgramProducer;
    }

    public String getPresenterName() {
        return radioProgramPresenter;
    }

    public String getDuration(){
        return radioProgramDuration;
    }
    public String getDate(){
        return date;
    }

    public String getStartTime(){
        return startTime;
    }

    public  String getAssignedBy(){
        return assignedBy;

    }


}
