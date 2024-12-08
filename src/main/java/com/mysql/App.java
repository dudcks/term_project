package com.mysql;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in); // Scanner는 프로그램 전체에서 1번만 생성
        boolean condition = true;
        try {
            // MySQL 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/UniversityDB",
                    "goyoungchan", "330931cks");
            System.out.println("MySQL 연결 완료!");
            while (condition) {
                System.out.println("메뉴");
                System.out.println("0. 종료\t 1.학생 추가\t 2.학생 찾기\t3.학생 삭제");
                System.out.println("6. 동아리 추가\t 7.동아리 찾기\t8.동아리 삭제\t9.");
                System.out.println("8. 교수 추가\t 9.교수 찾기\t10.교수 삭제");
                System.out.println("11. 활동 추가\t 12.활동 찾기\t13.활동 삭제\n");

                System.out.println("14. 동아리 가입\t 15.동아리 담당교수 입력\t16.동아리 회장 입력");
                System.out.println("17. 활동 참여\t 18.활동 회장 입력\t\n");
                
                
                System.out.println("19. 동아리 탈퇴\t20.활동 탈퇴\t21.동아리 담당교수 변경\t22.동아리 회장 변경");
                System.out.println("23. 동아리 소속 학생 정보\t 24.활동 참여 학생 정보");


                int menu = scanner.nextInt();
                scanner.nextLine();

                switch (menu) {
                    case 1:
                        StudentOperations.InsertStudent(con, scanner);
                        break;
                    case 2:
                        StudentOperations.FindStudent(con, scanner);
                        break;
                    case 3:
                        StudentOperations.DeleteStudent(con,scanner);
                        break;
                    case 4:
                        ClubOperations.InsertClub(con, scanner);
                        break;
                    case 5:
                        ClubOperations.FindClub(con, scanner);
                        break;
                    case 6:
                        ClubOperations.DeleteClub(con, scanner);
                        break;
                    case 0:
                        System.out.println("프로그램을 종료합니다.");
                        con.close();
                        condition = false;
                        break;
                    default:
                        System.out.println("올바른 메뉴를 선택하세요.");
                }
            }

        } catch (SQLException e) {
            System.out.println("MySQL 연결 실패: " + e.getMessage());
        }

        scanner.close();
    }
}
