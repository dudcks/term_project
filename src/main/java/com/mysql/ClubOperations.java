package com.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClubOperations {
        public static void InsertClub(Connection con, Scanner scanner) {
        try {
            System.out.print("ID: ");
            int cid = scanner.nextInt();
            scanner.nextLine();
            System.out.print("동아리 이름: ");
            String name = scanner.nextLine();
            System.out.print("설립일(YYYY-MM-DD): ");
            String creation_date = scanner.nextLine();
            System.out.print("위치: ");
            String location = scanner.nextLine();

            String insertQuery = "INSERT INTO Student (club_id, c_name, creation_date, location) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setInt(1, cid);
                pstmt.setString(2, name);
                pstmt.setString(3, creation_date);
                pstmt.setString(4, location);

                int insertCount = pstmt.executeUpdate();
                if (insertCount > 0) {
                    System.out.println("동아리 정보가 성공적으로 추가되었습니다.");
                }
            }
        } catch (SQLException e) {
            System.out.println("데이터 삽입 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void FindClub(Connection con, Scanner scanner) {
        System.out.print("이름:");
        String name = scanner.nextLine();
        
        try {
            String queryById = "SELECT * FROM Club WHERE c_name = ?";
            try (PreparedStatement pstmt = con.prepareStatement(queryById)) {
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();
            
                if (!rs.next()) {
                    System.out.println("해당 동아리 정보를 찾을 수 없습니다.");
                } else {
                    do {
                        System.out.println(rs.getInt("club_id") + " " + rs.getString("c_name") + " "
                                + rs.getString("creation_date") + " " + rs.getString("location"));
                    } while (rs.next());
                }
            }       
        } catch (SQLException e) {
            System.out.println("데이터 조회 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void DeleteClub(Connection con, Scanner scanner) {

        System.out.print("삭제할 동아리의 이름을 입력하세요: ");
        String name = scanner.nextLine();

        // 확인 메시지 출력
        System.out.print("정말로 " + name + " 동아리를 삭제하시겠습니까? (Y/N): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("Y")) {
            String deleteQuery = "DELETE FROM Club WHERE c_name = ?";

            try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
                pstmt.setString(1, name); 

                int deleteCount = pstmt.executeUpdate();
                if (deleteCount > 0) {
                    System.out.println("동아리 삭제 성공!");
                } else {
                    System.out.println("해당 동아리는 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                System.out.println("데이터 삭제 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("동아리 삭제가 취소되었습니다.");
        }
        System.out.println();
    }
}
