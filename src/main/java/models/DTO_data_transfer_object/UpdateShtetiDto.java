package models.DTO_data_transfer_object;

public class UpdateShtetiDto {
        private int id;
        private String shteti;
        private String shtetiEnglish;

        public UpdateShtetiDto(String shteti, String shtetiEnglish) {
            this.shteti = shteti;
            this.shtetiEnglish = shtetiEnglish;
        }
        public int getId() { return id; }

        public String getShteti() { return shteti; }

        public void setShteti(String shteti) { this.shteti = shteti; }

        public String getShtetiEnglish() { return shtetiEnglish; }

        public void setShtetiEnglish(String shtetiEnglish) { this.shtetiEnglish = shtetiEnglish; }
}
