package gloomhavenService.datamanagement.adapter.constants;

public class ScenarioSheet {

    public static class Sheet {

        public static class Scenario {
            public static final String NAME = "scenario";

            public static class Column {
                public static final String NUMBER = "number";
                public static final String NAME = "name";
                public static final String LOCATION = "location";
                public static final String GOAL = "goal";
                public static final String REWARD = "reward";
                public static final String LINKS = "links";
                public static final String STATUS = "status";
                public static final String REQUIREMENT_TYPE = "req_type";
            }
        }

        public static class Achievement {
            public static final String NAME = "achievement";

            public static class Column {
                public static final String NUMBER = "number";
                public static final String NAME = "name";
                public static final String TYPE = "type";
            }
        }

        public static class Trigger {
            public static final String NAME = "trigger";

            public static class Column {
                public static final String FROM = "from";
                public static final String TO = "to";
                public static final String ACTION = "action";
            }
        }

        public static class ScenarioAchievement {
            public static final String NAME = "achievement_rel";

            public static class Column {
                public static final String SCENARIO = "scenario";
                public static final String RELATION = "relation";
                public static final String ACHIEVEMENT = "achievement";
            }
        }
    }


}
