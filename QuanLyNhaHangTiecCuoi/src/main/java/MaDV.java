public enum MaDV {
    KARAOKE{
        public String layMaDV() {
            return "KR";
        }
    },
    TRANGTRI{
        public String layMaDV() {
            return "TT";
        }
    },
    THUECASI{
        public String layMaDV() {
            return "CS";
        }
    };
    public abstract String layMaDV();
    }
