package com.coco.bakingbuddy.admin.domain;

public enum ReportType {
    SPAM("스팸", "스팸으로 신고되었습니다."),
    INAPPROPRIATE_CONTENT("부적절한 콘텐츠", "부적절한 콘텐츠로 신고되었습니다."),
    HARASSMENT("불쾌감, 괴롭힘을 주는 콘텐츠", "불쾌감, 괴롭힘을 주는 콘텐츠로 신고되었습니다."),
    OTHER("기타", "");
    private final String displayName; // 신고자 신고 메뉴에 노출되는 메시지
    private final String completedMsg; // 신고 후 reported user가 받는 메시지

    ReportType(String displayName, String completedMsg) {
        this.displayName = displayName;
        this.completedMsg = completedMsg;
    }

}
