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
}
