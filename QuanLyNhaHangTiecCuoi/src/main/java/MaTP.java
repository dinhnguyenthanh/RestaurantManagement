public enum MaTP {
    THUCAN{
        public String layMaTP() {
            return "TA";
        }
    },
    THUCUONG{
        public String layMaTP() {
            return "TU";
        }
    };
    public abstract String layMaTP();
}
