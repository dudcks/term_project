package com.chan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ActivityOperations {

    // 활동 추가 메서드
    public static void InsertActivity(Connection con, Scanner scanner) {
        try {
            System.out.print("활동 ID: ");
            int activityId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("활동 이름: ");
            String activityName = scanner.nextLine();
            System.out.print("시작 날짜 (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            System.out.print("종료 날짜 (YYYY-MM-DD, 빈 칸일 경우 입력하지 않음): ");
            String endDate = scanner.nextLine();

            if (endDate.isEmpty()) {
                endDate = null;
            }

            String insertQuery = "INSERT INTO Activity (activity_id, a_name, start_date, end_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setInt(1, activityId);
                pstmt.setString(2, activityName);
                pstmt.setString(3, startDate);
                pstmt.setString(4, endDate);

                int insertCount = pstmt.executeUpdate();
                if (insertCount > 0) {
                    System.out.println("활동 정보가 성공적으로 추가되었습니다.");
                }
            }
        } catch (SQLException e) {
            System.out.println("데이터 삽입 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void UpdateActivityEndDate(Connection con, Scanner scanner) {
        try {
            System.out.print("활동 이름: ");
            String activityName = scanner.nextLine();
            System.out.print("종료 날짜 (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();

            // 활동 이름으로 activity_id 조회
            String findActivityQuery = "SELECT activity_id FROM Activity WHERE a_name = ?";
            try (PreparedStatement findStmt = con.prepareStatement(findActivityQuery)) {
                findStmt.setString(1, activityName);
                ResultSet rs = findStmt.executeQuery();

                if (rs.next()) {
                    int activityId = rs.getInt("activity_id");

                    // 종료 날짜 업데이트
                    String updateQuery = "UPDATE Activity SET end_date = ? WHERE activity_id = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
                        pstmt.setString(1, endDate);
                        pstmt.setInt(2, activityId);

                        int updateCount = pstmt.executeUpdate();
                        if (updateCount > 0) {
                            System.out.println("활동 '" + activityName + "'의 종료 날짜가 성공적으로 업데이트되었습니다.");
                        } else {
                            System.out.println("종료 날짜 업데이트에 실패했습니다.");
                        }
                    }
                } else {
                    System.out.println("입력한 활동 이름이 존재하지 않습니다.");
                }
            }
        } catch (SQLException e) {
            System.out.println("종료 날짜 업데이트 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }


    public static void FindActivity(Connection con, Scanner scanner) {
        System.out.println("1. 활동 ID로 찾기\t2. 활동 이름으로 찾기\t3. 취소");
        int menu = scanner.nextInt();
        scanner.nextLine();
        
        try {
            switch (menu) {
                case 1:
                    System.out.print("활동 ID: ");
                    int activityId = scanner.nextInt();
                    scanner.nextLine();

                    String queryById = "SELECT * FROM Activity WHERE activity_id = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(queryById)) {
                        pstmt.setInt(1, activityId);
                        ResultSet rs = pstmt.executeQuery();
                    
                        if (!rs.next()) {
                            System.out.println("해당 ID의 활동 정보를 찾을 수 없습니다.");
                        } else {
                            do {
                                System.out.println(rs.getInt("activity_id") + " " + rs.getString("a_name") + " "
                                        + rs.getDate("start_date") + " " + rs.getDate("end_date"));
                            } while (rs.next());
                        }
                    }                    
                    break;
                case 2:
                    System.out.print("활동 이름: ");
                    String name = scanner.nextLine();

                    String queryByName = "SELECT * FROM Activity WHERE a_name = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(queryByName)) {
                        pstmt.setString(1, name);
                        ResultSet rs = pstmt.executeQuery();
                    
                        if (!rs.next()) {
                            System.out.println("해당 이름의 활동 정보를 찾을 수 없습니다.");
                        } else {
                            do {
                                System.out.println(rs.getInt("activity_id") + " " + rs.getString("a_name") + " "
                                        + rs.getDate("start_date") + " " + rs.getDate("end_date"));
                            } while (rs.next());
                        }
                    }
                    break;
                case 3:
                    System.out.println("취소되었습니다.");
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
            }
        } catch (SQLException e) {
            System.out.println("데이터 조회 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void DeleteActivity(Connection con, Scanner scanner) {
        System.out.print("삭제할 활동의 ID를 입력하세요: ");
        int activityId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("정말로 ID " + activityId + " 활동을 삭제하시겠습니까? (Y/N): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("Y")) {
            String deleteQuery = "DELETE FROM Activity WHERE activity_id = ?";

            try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, activityId);

                int deleteCount = pstmt.executeUpdate();
                if (deleteCount > 0) {
                    System.out.println("활동 삭제 성공!");
                } else {
                    System.out.println("해당 ID의 활동이 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                System.out.println("데이터 삭제 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("활동 삭제가 취소되었습니다.");
        }
        System.out.println();
    }

    public static void Join_Activity(Connection con, Scanner scanner) {
        System.out.println("1.입력\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();

        if (menu == 1) {
            try {
                System.out.print("학번: ");
                int sid = scanner.nextInt();
                scanner.nextLine();

                System.out.print("활동 이름: ");
                String activityName = scanner.nextLine();

                // 역할 입력
                System.out.print("활동 내 역할 (예: 팀원, 리더 등): ");
                String role = scanner.nextLine();

                System.out.print("활동 시간 (시간 단위로 입력): ");
                int hours = scanner.nextInt();
                scanner.nextLine();

                // 활동 이름으로 activity_id 조회
                String findActivityQuery = "SELECT activity_id FROM Activity WHERE a_name = ?";
                try (PreparedStatement findActivityStmt = con.prepareStatement(findActivityQuery)) {
                    findActivityStmt.setString(1, activityName);

                    try (ResultSet rs = findActivityStmt.executeQuery()) {
                        if (rs.next()) {
                            int activityId = rs.getInt("activity_id");

                            String insertQuery = """
                            INSERT INTO Student_Activity (student_id, activity_id, role, hours) VALUES (?, ?, ?, ?)
                            """;
                            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, sid);
                                insertStmt.setInt(2, activityId);
                                insertStmt.setString(3, role);
                                insertStmt.setInt(4, hours);

                                int insertCount = insertStmt.executeUpdate();
                                if (insertCount > 0) {
                                    System.out.println("활동 참여가 성공적으로 완료되었습니다.");
                                }
                            }
                        } else {
                            System.out.println("입력한 활동 이름이 존재하지 않습니다.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("취소되었습니다.");
        }
        System.out.println();
    }


    public static void President_Activity_student(Connection con, Scanner scanner) {
        System.out.println("1.입력\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();

        if (menu == 1) {
            try {
                System.out.print("학번: ");
                int sid = scanner.nextInt();
                scanner.nextLine();

                System.out.print("활동 이름: ");
                String clubName = scanner.nextLine();

                // 동아리 이름으로 club_id 조회
                String findClubQuery = "SELECT activity_id FROM Activity WHERE a_name = ?";
                try (PreparedStatement findClubStmt = con.prepareStatement(findClubQuery)) {
                    findClubStmt.setString(1, clubName);

                    try (ResultSet rs = findClubStmt.executeQuery()) {
                        if (rs.next()) {
                            int clubId = rs.getInt("activity_id");

                            String insertQuery = "INSERT INTO Activity_president (student_id, activity_id) VALUES (?, ?)";
                            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, sid);
                                insertStmt.setInt(2, clubId);

                                int insertCount = insertStmt.executeUpdate();
                                if (insertCount > 0) {
                                    System.out.println("활동 대표 입력이 성공적으로 완료 되었습니다.");
                                }
                            }
                        } else {
                            System.out.println("입력한 활동 이름이 존재하지 않습니다.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("취소되었습니다.");
        }
        System.out.println();
    }

    public static void LeaveActivity(Connection con, Scanner scanner) {
        System.out.println("1.탈퇴\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();

        if (menu == 1) {
            try {
                System.out.print("학번: ");
                int sid = scanner.nextInt();
                scanner.nextLine();

                System.out.print("활동 이름: ");
                String activityName = scanner.nextLine();

                System.out.print("정말로 활동에서 탈퇴하시겠습니까? (Y/N): ");
                String confirm = scanner.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {
                    // 활동 이름으로 activity_id를 조회 후 삭제
                    String deleteQuery = "DELETE FROM Student_Activity " +
                            "WHERE student_id = ? AND activity_id = (SELECT activity_id FROM Activity WHERE a_name = ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
                        pstmt.setInt(1, sid);
                        pstmt.setString(2, activityName);

                        int deleteCount = pstmt.executeUpdate();
                        if (deleteCount > 0) {
                            System.out.println("활동 탈퇴가 성공적으로 완료되었습니다.");
                        } else {
                            System.out.println("해당 학번과 활동 이름에 해당하는 정보가 없습니다.");
                        }
                    }
                } else {
                    System.out.println("활동 탈퇴가 취소되었습니다.");
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("활동 탈퇴가 취소되었습니다.");
        }
        System.out.println();
    }

    public static void GetActivityParticipants(Connection con, Scanner scanner) {
        System.out.println("1.활동 참여 학생 확인\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();

        if (menu == 1) {
            try {
                // 활동 이름 입력
                System.out.print("활동 이름: ");
                String activityName = scanner.nextLine();

                // 활동 ID 확인
                String getActivityIdQuery = "SELECT activity_id FROM Activity WHERE a_name = ?";
                try (PreparedStatement activityStmt = con.prepareStatement(getActivityIdQuery)) {
                    activityStmt.setString(1, activityName);
                    ResultSet activityRs = activityStmt.executeQuery();

                    if (activityRs.next()) {
                        int activityId = activityRs.getInt("activity_id");

                        // 활동 참여 학생 정보 가져오기
                        String getParticipantsQuery = """
                        SELECT s.student_id, s.s_name, sa.role, sa.hours
                        FROM Student_Activity sa
                        JOIN Student s ON sa.student_id = s.student_id
                        WHERE sa.activity_id = ?
                    """;
                        try (PreparedStatement participantsStmt = con.prepareStatement(getParticipantsQuery)) {
                            participantsStmt.setInt(1, activityId);
                            ResultSet participantsRs = participantsStmt.executeQuery();

                            System.out.println("학생 정보:");
                            boolean hasParticipants = false;
                            while (participantsRs.next()) {
                                hasParticipants = true;
                                System.out.printf("학번: %d, 이름: %s, 역할: %s, 시간: %d\n",
                                        participantsRs.getInt("student_id"),
                                        participantsRs.getString("s_name"),
                                        participantsRs.getString("role"),
                                        participantsRs.getInt("hours"));
                            }
                            if (!hasParticipants) {
                                System.out.println("해당 활동에 참여한 학생이 없습니다.");
                            }
                        }
                    } else {
                        System.out.println("해당 이름의 활동이 존재하지 않습니다.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("활동 참여 학생 확인이 취소되었습니다.");
        }
        System.out.println();
    }

    public static void UpdateActivityHours(Connection con, Scanner scanner) {
        System.out.println("1.변경\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();

        if (menu == 1) {
            try {
                // 학번 입력
                System.out.print("학번: ");
                int sid = scanner.nextInt();
                scanner.nextLine();

                // 활동 이름 입력
                System.out.print("활동 이름: ");
                String activityName = scanner.nextLine();

                // 새로운 활동 시간 입력
                System.out.print("새로운 활동 시간 (시간 단위로 입력): ");
                int newHours = scanner.nextInt();
                scanner.nextLine();

                // 활동 이름으로 activity_id 조회
                String findActivityQuery = "SELECT activity_id FROM Activity WHERE a_name = ?";
                try (PreparedStatement findActivityStmt = con.prepareStatement(findActivityQuery)) {
                    findActivityStmt.setString(1, activityName);

                    try (ResultSet rs = findActivityStmt.executeQuery()) {
                        if (rs.next()) {
                            int activityId = rs.getInt("activity_id");

                            // 활동 시간 업데이트
                            String updateQuery = """
                            UPDATE Student_Activity 
                            SET hours = ? 
                            WHERE student_id = ? AND activity_id = ?
                        """;
                            try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                                updateStmt.setInt(1, newHours);
                                updateStmt.setInt(2, sid);
                                updateStmt.setInt(3, activityId);

                                int updateCount = updateStmt.executeUpdate();
                                if (updateCount > 0) {
                                    System.out.println("활동 시간이 성공적으로 변경되었습니다.");
                                } else {
                                    System.out.println("해당 학생과 활동 정보가 존재하지 않습니다.");
                                }
                            }
                        } else {
                            System.out.println("입력한 활동 이름이 존재하지 않습니다.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("취소되었습니다.");
        }
        System.out.println();
    }


}
