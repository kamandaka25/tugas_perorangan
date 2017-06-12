package id.sch.smktelkom_mlg.privateassignment.xirpl236.individu;

/**
 * Created by Kamandaka on 6/12/2017.
 */

public class Note {
    private int id;
    private int money;
    private String hal;
    private String date;

        public Note(){

        }
        public Note(int money,String hal,int id,String date){
            this.money=money;
            this.hal=hal;
            this.id=id;
            this.date=date;
        }
        public void setId(int id){
            this.id=id;
        }
        public void setMoney(int money){
            this.money=money;
        }
        public void setHal(String hal){
            this.hal=hal;
        }
        public void setDate(String date){
            this.date=date;
        }
        public int getId(){
            return id;
        }
        public int getMoney(){
            return money;
        }
        public String getHal(){
            return hal;
        }
        public String getDate(){
            return date;
        }

}
