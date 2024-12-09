package com.chan;

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

    public static void JoinClub(Connection con, Scanner scanner) {
        System.out.println("1.입력\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();
    
        if (menu == 1) {
            try {
                System.out.print("학번: ");
                int sid = scanner.nextInt();
                scanner.nextLine(); 
        
                System.out.print("동아리 이름: ");
                String clubName = scanner.nextLine();
    
                // 동아리 이름으로 club_id 조회
                String findClubQuery = "SELECT club_id FROM Club WHERE c_name = ?";
                try (PreparedStatement findClubStmt = con.prepareStatement(findClubQuery)) {
                    findClubStmt.setString(1, clubName);
    
                    try (ResultSet rs = findClubStmt.executeQuery()) {
                        if (rs.next()) {
                            int clubId = rs.getInt("club_id");
    
                            String insertQuery = "INSERT INTO Student_Club (student_id, club_id) VALUES (?, ?)";
                            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, sid);
                                insertStmt.setInt(2, clubId);
    
                                int insertCount = insertStmt.executeUpdate();
                                if (insertCount > 0) {
                                    System.out.println("동아리에 성공적으로 가입되었습니다.");
                                }
                            }
                        } else {
                            System.out.println("입력한 동아리 이름이 존재하지 않습니다.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("동아리 가입이 취소되었습니다.");
        }
        System.out.println();
    }


    public static void President_club_professor(Connection con, Scanner scanner) {
        System.out.println("1.입력\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();

        if (menu == 1) {
            try {
                System.out.print("교수 ID: ");
                int sid = scanner.nextInt();
                scanner.nextLine();

                System.out.print("동아리 이름: ");
                String clubName = scanner.nextLine();

                // 동아리 이름으로 club_id 조회
                String findClubQuery = "SELECT club_id FROM Club WHERE c_name = ?";
                try (PreparedStatement findClubStmt = con.prepareStatement(findClubQuery)) {
                    findClubStmt.setString(1, clubName);

                    try (ResultSet rs = findClubStmt.executeQuery()) {
                        if (rs.next()) {
                            int clubId = rs.getInt("club_id");

                            String insertQuery = "INSERT INTO Professor_Club (professor_id, club_id) VALUES (?, ?)";
                            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, sid);
                                insertStmt.setInt(2, clubId);

                                int insertCount = insertStmt.executeUpdate();
                                if (insertCount > 0) {
                                    System.out.println("동아리에 담당교수 입력이 성공적이로 완료 되었습니다.");
                                }
                            }
                        } else {
                            System.out.println("입력한 동아리 이름이 존재하지 않습니다.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("동아리 담당교수 입력이 취소되었습니다.");
        }
        System.out.println();
    }

    public static void President_club_student(Connection con, Scanner scanner) {
        System.out.println("1.입력\t2.취소");
        int menu = scanner.nextInt();
        scanner.nextLine();

        if (menu == 1) {
            try {
                System.out.print("학번: ");
                int sid = scanner.nextInt();
                scanner.nextLine();

                System.out.print("동아리 이름: ");
                String clubName = scanner.nextLine();

                // 동아리 이름으로 club_id 조회
                String findClubQuery = "SELECT club_id FROM Club WHERE c_name = ?";
                try (PreparedStatement findClubStmt = con.prepareStatement(findClubQuery)) {
                    findClubStmt.setString(1, clubName);

                    try (ResultSet rs = findClubStmt.executeQuery()) {
                        if (rs.next()) {
                            int clubId = rs.getInt("club_id");

                            String insertQuery = "INSERT INTO Club_president (student_id, club_id) VALUES (?, ?)";
                            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, sid);
                                insertStmt.setInt(2, clubId);

                                int insertCount = insertStmt.executeUpdate();
                                if (insertCount > 0) {
                                    System.out.println("동아리에 회장 입력이 성공적이로 완료 되었습니다.");
                                }
                            }
                        } else {
                            System.out.println("입력한 동아리 이름이 존재하지 않습니다.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("데이터 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("동아리 회장 입력이 취소되었습니다.");
        }
        System.out.println();
    }
    
}
