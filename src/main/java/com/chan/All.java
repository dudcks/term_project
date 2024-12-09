package com.chan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class All {
    public static void ViewAllStudents(Connection con) {
        String query = """
        SELECT s.student_id, s.s_name,
               IFNULL(c.c_name, 'NULL') AS club_name
        FROM Student s
        LEFT JOIN Student_Club sc ON s.student_id = sc.student_id
        LEFT JOIN Club c ON sc.club_id = c.club_id
        ORDER BY s.student_id;
    """;

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("학생 ID\t이름\t\t소속 동아리");
            while (rs.next()) {
                System.out.printf("%d\t%s\t%s%n",
                        rs.getInt("student_id"),
                        rs.getString("s_name"),
                        rs.getString("club_name"));
            }
        } catch (SQLException e) {
            System.out.println("데이터 조회 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void ViewAllClubs(Connection con) {
        String query = """
    SELECT c.club_id, c.c_name,
           IFNULL(s.s_name, 'NULL') AS president_name,
           IFNULL(p.p_name, 'NULL') AS professor_name
    FROM Club c
    LEFT JOIN Club_president cp ON c.club_id = cp.club_id
    LEFT JOIN Student s ON cp.student_id = s.student_id
    LEFT JOIN Professor_Club pc ON c.club_id = pc.club_id
    LEFT JOIN Professor p ON pc.professor_id = p.professor_id
    ORDER BY c.club_id;
    """;

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("동아리 ID\t동아리 이름\t회장\t\t담당 교수");
            while (rs.next()) {
                System.out.printf("%d\t%s\t\t%s\t\t%s%n",
                        rs.getInt("club_id"),
                        rs.getString("c_name"),
                        rs.getString("president_name"),
                        rs.getString("professor_name"));
            }
        } catch (SQLException e) {
            System.out.println("데이터 조회 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }


    public static void ViewAllActivities(Connection con) {
        String query = """
        SELECT a.activity_id, a.a_name, a.start_date, a.end_date
        FROM Activity a
        ORDER BY a.activity_id;
    """;

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("활동 ID\t활동 이름\t\t시작 날짜\t\t종료 날짜");
            while (rs.next()) {
                System.out.printf("%d\t%s\t%s\t%s%n",
                        rs.getInt("activity_id"),
                        rs.getString("a_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date") != null ? rs.getDate("end_date").toString() : "NULL");
            }
        } catch (SQLException e) {
            System.out.println("데이터 조회 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

    public static void ViewAllProfessors(Connection con) {
        String query = "SELECT professor_id, p_name, department FROM Professor";

        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("모든 교수 정보:");
            System.out.println("ID\t이름\t학과");

            while (rs.next()) {
                int professorId = rs.getInt("professor_id");
                String professorName = rs.getString("p_name");
                String department = rs.getString("department");
                System.out.println(professorId + "\t" + professorName + "\t" + department);
            }
        } catch (SQLException e) {
            System.out.println("데이터 조회 중 오류 발생: " + e.getMessage());
        }
        System.out.println();
    }

}
