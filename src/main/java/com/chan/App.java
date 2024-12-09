package com.chan;
import java.util.Scanner;
import java.sql.*; 

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in, "UTF-8");
        boolean condition = true;
        try {
            // MySQL 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/UniversityDB?useUnicode=true&characterEncoding=utf8",
                    "goyoungchan", "330931cks");
            System.out.println("MySQL 연결 완료!");
            while (condition) {
                System.out.println("_______________________________________________________________________________________________________");
                System.out.println("메뉴");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.printf("%-3s %-30s %-3s %-30s %-3s %-30s %-3s %-30s\n",
                        "0.", "종료",
                        "1.", "학생 추가",
                        "2.", "학생 찾기",
                        "3.", "학생 삭제");
                System.out.printf("%-3s %-30s %-3s %-30s %-3s %-30s %-3s %-30s\n",
                        "4.", "동아리 추가",
                        "5.", "동아리 찾기",
                        "6.", "동아리 삭제",
                        "7.", "교수 추가");
                System.out.printf("%-3s %-30s %-3s %-30s %-3s %-30s %-3s %-30s\n",
                        "8.", "교수 찾기",
                        "9.", "교수 삭제",
                        "10.", "활동 추가",
                        "11.", "활동 찾기");
                System.out.printf("%-3s %-30s %-3s %-30s %-3s %-30s %-3s %-30s\n",
                        "12.", "활동 삭제",
                        "13.", "동아리 가입",
                        "14.", "동아리 담당교수 입력",
                        "15.", "동아리 회장 입력");
                System.out.printf("%-3s %-30s %-3s %-30s %-3s %-30s %-3s %-30s\n",
                        "16.", "활동 참여",
                        "17.", "활동 대표 입력",
                        "18.", "동아리 탈퇴",
                        "19.", "활동 탈퇴");
                System.out.printf("%-3s %-30s %-3s %-30s %-3s %-30s %-3s %-30s\n",
                        "20.", "동아리 담당교수 변경",
                        "21.", "동아리 회장 변경",
                        "22.", "동아리 소속 학생 정보",
                        "23.", "활동 참여 학생 정보");
                System.out.printf("%-3s %-30s %-3s %-30s %-3s %-30s %-3s %-30s\n",
                        "24.", "활동 시간 변경",
                        "25.", "모든 학생",
                        "26.", "모든 동아리",
                        "27.", "모든 활동");
                System.out.printf("%-3s %-30s %-3s %-30s\n", "28.", "활동 종료일 입력","29.", "모든 교수 확인");
                System.out.println("--------------------------------------------------------------------------------------------------------");

                System.out.print("입력: ");

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
                    case 14:
                        ClubOperations.President_club_professor(con,scanner);
                        break;
                    case 15:
                        ClubOperations.President_club_student(con,scanner);
                        break;
                    case 16:
                        ActivityOperations.Join_Activity(con,scanner);
                        break;
                    case 17:
                        ActivityOperations.President_Activity_student(con,scanner);
                        break;
                    case 18:
                        ClubOperations.LeaveClub(con,scanner);
                        break;
                    case 19:
                        ActivityOperations.LeaveActivity(con,scanner);
                        break;
                    case 20:
                        ClubOperations.UpdateClubAdvisor(con,scanner);
                        break;
                    case 21:
                        ClubOperations.UpdateClubPresident(con,scanner);
                        break;
                    case 22:
                        ClubOperations.GetClubMembers(con,scanner);
                        break;
                    case 23:
                        ActivityOperations.GetActivityParticipants(con,scanner);
                        break;
                    case 24:
                        ActivityOperations.UpdateActivityHours(con,scanner);
                        break;
                    case 25:
                        All.ViewAllStudents(con);
                        break;
                    case 26:
                        All.ViewAllClubs(con);
                        break;
                    case 27:
                        All.ViewAllActivities(con);
                        break;
                    case 28:
                        ActivityOperations.UpdateActivityEndDate(con,scanner);
                        break;
                    case 29:
                        All.ViewAllProfessors(con);
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
