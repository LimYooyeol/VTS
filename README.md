# VTS(Virtual Trading System)
광운대학교 22학년도 2학기 '데이터베이스 및 시각화' 강의를 통해 진행한 프로젝트로, **가상 주식 거래 서비스**를 개발한 프로젝트입니다. 

## 목차
* [프로젝트 개요](#프로젝트-개요)
* [개발환경](#개발환경)
* [주요기능](#주요기능)
* [데이터베이스 설계](#데이터베이스-설계)
* [팀원](#팀원)

## 프로젝트 개요
본 프로젝트는 **가상 주식 거래 사이트**를 개발한 프로젝트입니다.  
주식 매매, 기업 조회, 거래 기록 조회 등의 주식 거래 관련 기능과 종목 토론 게시판을 제공합니다.

## 개발환경
* **백엔드**
  * **언어**: JAVA
  * **프레임워크**: Spring Boot, Spring Security, MyBatis
  * **라이브러리**: Lombok, JUnit5
  * **DB**: MySQL
  * **IDE**: IntelliJ
* **프론트엔드**
  * **언어**: HTML, CSS, JavaScript(JQuery)
  * **Template engine**: Thymeleaf
  * **API**: Google Chart API

## 주요기능
* **이용자 기능**
  * 로그인
  * 회원가입
  * 마이페이지
* **기업 관련 기능**
  * 기업 검색
  * 차트 조회
  * 기업 상세 정보 조회
  * 관심 기업 관리
* **주식매매 기능**
  * 주식매매
  * 보유 자산 현황
  * 거래 내역 조회
* **종목 토론 게시판**
  * 게시물 작성
  * 게시물 수정/삭제
  * 게시물 검색
  * 댓글 작성
  * 댓글 삭제
* **관리자 기능**
  * 회원 삭제
  * 회원 정보 수정
  * 기업 삭제
* **특별 기능**
  * 특별 종목(거래량 상위, 상승률 상위, 하락률 상위) 목록 조회
  * 최근 상승 섹터 조회
  * 오늘의 주식왕 - 상승률 상위 3개 종목 보유 회원 조회
 
## 데이터베이스 설계

### ER diagram
<p align = "center">
<img src = "https://user-images.githubusercontent.com/71579787/210544032-762ee56f-144f-45ec-9b6f-7ff68bfa7ac7.png" width = "700">
</p>


### Table Schema
<p align = "center">
<img src = "https://user-images.githubusercontent.com/71579787/210544428-3bfd2312-5964-46b7-984d-dd9c0a4e9e52.png" width = "700">
</p>


### DDL
src/main/resources/ddl.sql 

## 팀원
김세진: 정적 페이지 개발  
임유열: 백엔드 개발, 동적 페이지 개발


