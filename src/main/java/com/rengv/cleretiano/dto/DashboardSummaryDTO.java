package com.rengv.cleretiano.dto;

public class DashboardSummaryDTO {

    private long totalStudents;
    private long totalTeachers;
    private long totalCourses;
    private long totalGrades;
    private long totalSections;
    private long totalNotes;

    public DashboardSummaryDTO() {
    }

    public DashboardSummaryDTO(long totalStudents, long totalTeachers, long totalCourses,
                                long totalGrades, long totalSections, long totalNotes) {
        this.totalStudents = totalStudents;
        this.totalTeachers = totalTeachers;
        this.totalCourses = totalCourses;
        this.totalGrades = totalGrades;
        this.totalSections = totalSections;
        this.totalNotes = totalNotes;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(long totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getTotalGrades() {
        return totalGrades;
    }

    public void setTotalGrades(long totalGrades) {
        this.totalGrades = totalGrades;
    }

    public long getTotalSections() {
        return totalSections;
    }

    public void setTotalSections(long totalSections) {
        this.totalSections = totalSections;
    }

    public long getTotalNotes() {
        return totalNotes;
    }

    public void setTotalNotes(long totalNotes) {
        this.totalNotes = totalNotes;
    }
}
