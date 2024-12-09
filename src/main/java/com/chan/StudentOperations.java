package com.chan;

import java.sql.*;
import java.util.Scanner;

public class StudentOperations {
    public static void InsertStudent(Connection con, Scanner scanner) {
        try {
            System.out.print("학번: ");
            int sid = scanner.nextInt();
            scanner.nextLine();
            System.out.print("이름: ");
            String name = scanner.nextLine();
            System.out.print("생년월일(YYYY-MM-DD): ");
            String b_date = scanner.nextLine();
            System.out.print("성별(F,M): ");
            String gender = scanner.nextLine();

            String insertQuery = "INSERT INTO Student (student_id, s_name, birth_date, gender) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setInt(1, sid);
                pstmt.setString(2, name);
                pstmt.setString(3, b_date);
                pstmt.setString(4, gender);
                

                int insertCount = pstmt.executeUpdate();
                if (insertCount > 0) {
                    System.out.println("학생 정보가 성공적으로 추가되었습니다.");
                }
            }
        } catch (SQLException e) {
            System.out.println("데이터 삽입 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void FindStudent(Connection con, Scanner scanner) {
        System.out.println("1.학번으로 찾기\t2.이름으로 찾기\t3.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();
        

        try {
            switch (menu) {
                case 1:
                    System.out.print("학번: ");
                    int sid = scanner.nextInt();
                    scanner.nextLine();

                    String queryById = "SELECT * FROM Student WHERE student_id = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(queryById)) {
                        pstmt.setInt(1, sid);
                        ResultSet rs = pstmt.executeQuery();
                    
                        if (!rs.next()) {
                            System.out.println("해당 학번의 학생 정보를 찾을 수 없습니다.");
                        } else {
                            do {
                                System.out.println("학번  |  이름  |  생일  |  성별");
                                System.out.println(rs.getInt("student_id") + " " + rs.getString("s_name") + " "
                                        + rs.getString("birth_date") + " " + rs.getString("gender"));
                            } while (rs.next());
                        }
                    }                    
                    break;
                case 2:
                    System.out.print("이름: ");
                    String name = scanner.nextLine();

                    String queryByName = "SELECT * FROM Student WHERE s_name = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(queryByName)) {
                        pstmt.setString(1, name);
                        ResultSet rs = pstmt.executeQuery();
                    
                        if (!rs.next()) {
                            System.out.println("해당 이름의 학생 정보를 찾을 수 없습니다.");
                        } else {
                            do {
                                System.out.println("학번  |  이름  |  생일  |  성별");
                                System.out.println(rs.getInt("student_id") + " " + rs.getString("s_name") + " "
                                        + rs.getString("birth_date") + " " + rs.getString("gender"));
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

    public static void DeleteStudent(Connection con, Scanner scanner) {

        System.out.print("삭제할 학생의 학번을 입력하세요: ");
        int sid = scanner.nextInt();
        scanner.nextLine();

        // 확인 메시지 출력
        System.out.print("정말로 학번 " + sid + " 학생을 삭제하시겠습니까? (Y/N): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("Y")) {
            String deleteQuery = "DELETE FROM Student WHERE student_id = ?";

            try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, sid); 

                int deleteCount = pstmt.executeUpdate();
                if (deleteCount > 0) {
                    System.out.println("학생 삭제 성공!");
                } else {
                    System.out.println("해당 학번의 학생이 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                System.out.println("데이터 삭제 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("학생 삭제가 취소되었습니다.");
        }
        System.out.println();
    }
    
}