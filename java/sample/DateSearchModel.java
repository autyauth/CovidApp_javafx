package sample;

public class DateSearchModel {
    String date;
    Integer infect;
    Integer recover;
    Integer death;

    public DateSearchModel(String date, Integer infect, Integer recover, Integer death) {
        this.date = date;
        this.infect = infect;
        this.recover = recover;
        this.death = death;
    }

    public String getDate() {
        return date;
    }

    public Integer getInfect() {
        return infect;
    }

    public Integer getRecover() {
        return recover;
    }

    public Integer getDeath() {
        return death;
    }


    public void setDate่(String date) {
        this.date = date;
    }

    public void setInfect(Integer infect) {
        this.infect = infect;
    }

    public void setRecover(Integer recover) {
        this.recover = recover;
    }

    public void setDeath(Integer death) {
        this.death = death;
    }
}



