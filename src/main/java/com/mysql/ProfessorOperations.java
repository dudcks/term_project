package com.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProfessorOperations {
    public static void InsertProfessor(Connection con, Scanner scanner) {
        try {
            System.out.print("ID: ");
            int pid = scanner.nextInt();
            scanner.nextLine();
            System.out.print("이름: ");
            String name = scanner.nextLine();
            System.out.print("학과: ");
            String department = scanner.nextLine();

            String insertQuery = "INSERT INTO Professor (professor_id, p_name, department) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setInt(1,pid);
                pstmt.setString(2, name);
                pstmt.setString(4, department);

                int insertCount = pstmt.executeUpdate();
                if (insertCount > 0) {
                    System.out.println("교수 정보가 성공적으로 추가되었습니다.");
                }
            }
        } catch (SQLException e) {
            System.out.println("데이터 삽입 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void FindProfessor(Connection con, Scanner scanner) {
        System.out.println("1.ID로 찾기\t2.이름으로 찾기\t3.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();
        

        try {
            switch (menu) {
                case 1:
                    System.out.print("ID: ");
                    int sid = scanner.nextInt();
                    scanner.nextLine();

                    String queryById = "SELECT * FROM Professor WHERE professor_id = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(queryById)) {
                        pstmt.setInt(1, sid);
                        ResultSet rs = pstmt.executeQuery();
                    
                        if (!rs.next()) {
                            System.out.println("해당 ID의 교수 정보를 찾을 수 없습니다.");
                        } else {
                            do {
                                System.out.println(rs.getInt("professor_id") + " " + rs.getString("p_name") + " "
                                        + rs.getString("department") );
                            } while (rs.next());
                        }
                    }                    
                    break;
                case 2:
                    System.out.print("이름: ");
                    String name = scanner.nextLine();

                    String queryByName = "SELECT * FROM Professor WHERE p_name = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(queryByName)) {
                        pstmt.setString(1, name);
                        ResultSet rs = pstmt.executeQuery();
                    
                        if (!rs.next()) {
                            System.out.println("해당 이름의 교수 정보를 찾을 수 없습니다.");
                        } else {
                            do {
                                System.out.println(rs.getInt("professor_id") + " " + rs.getString("p_name") + " "
                                        + rs.getString("department") );
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

    public static void DeleteProfessor(Connection con, Scanner scanner) {

        System.out.print("삭제할 교수의 ID을 입력하세요: ");
        int sid = scanner.nextInt();
        scanner.nextLine();

        // 확인 메시지 출력
        System.out.print("정말로 ID " + sid + " 교수를 삭제하시겠습니까? (Y/N): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("Y")) {
            String deleteQuery = "DELETE FROM Professor WHERE professor_id = ?";

            try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, sid); 

                int deleteCount = pstmt.executeUpdate();
                if (deleteCount > 0) {
                    System.out.println("교수 삭제 성공!");
                } else {
                    System.out.println("해당 ID의 교수가 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                System.out.println("데이터 삭제 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("교수 삭제가 취소되었습니다.");
        }
        System.out.println();
    }
}
