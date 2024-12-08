package com.mysql;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in); 
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
                System.out.println("4. 동아리 추가\t 5.동아리 찾기\t6.동아리 삭제\t");
                System.out.println("7. 교수 추가\t 8.교수 찾기\t9.교수 삭제");
                System.out.println("10. 활동 추가\t 11.활동 찾기\t12.활동 삭제\n");
                //-------

                System.out.println("13. 동아리 가입\t 16.동아리 담당교수 입력\t17.동아리 회장 입력");
                System.out.println("18. 활동 참여\t 19.활동 회장 입력\t\n");
                
                
                System.out.println("20. 동아리 탈퇴\t21.활동 탈퇴\t22.동아리 담당교수 변경\t23.동아리 회장 변경");
                System.out.println("24. 동아리 소속 학생 정보\t 25.활동 참여 학생 정보");


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
                    case 7:
                        ProfessorOperations.InsertProfessor(con, scanner);
                        break;
                    case 8:
                        ProfessorOperations.FindProfessor(con, scanner);
                        break;
                    case 9:
                        ProfessorOperations.DeleteProfessor(con, scanner);
                        break;
                    case 10:
                        ActivityOperations.InsertActivity(con, scanner);
                        break;
                    case 11:
                        ActivityOperations.FindActivity(con, scanner);
                        break;
                    case 12:
                        ActivityOperations.DeleteActivity(con, scanner);
                        break;   
                    case 13:
                        ClubOperations.JoinClub(con, scanner);
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
