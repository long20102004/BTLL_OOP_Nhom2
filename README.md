# BÁO CÁO KẾT QUẢ BÀI TẬP LỚN OOP
## 1. Thông tin nhóm
**Tên Dự Án:** DEVERS

**Link Dự Án:** https://github.com/long20102004/devers

**Thành Viên Nhóm:**
- Hoàng Hải Long
- Nguyễn Thị Ngân
- Nguyễn Đình Phúc 
- Đoàn Thảo Vân
- Trần Khánh Nhật

**Mô hình làm việc**
Team hoạt động theo mô hình Scrum, sử dụng Linear để quản lý công việc. Các công việc được keep track đầy đủ trên Linear.

- Link linear: https://linear.app/nhom2-oop/team/NHOM2/all

Mỗi tuần, team sẽ ngồi lại để review công việc đã làm, cùng nhau giải quyết vấn đề và đề xuất các tính năng phát triển cho tuần tiếp theo.

**Version Control Strategy**

Team hoạt động theo Gitflow để quản lý code. Mỗi thành viên sẽ tạo branch từ develop để làm việc, các branch đặt theo format feature/ten-chuc-nang, sau khi hoàn thành sẽ tạo Pull Request để review code và merge vào develop

- Các nhánh chính:
  + `master`: Chứa code ổn định, đã qua kiểm tra và test kỹ lưỡng
  + `develop`: Chứa code mới nhất, đã qua review và test
  + `feature/`: Các nhánh chứa code đang phát triển, short-live, sau khi hoàn thành sẽ merge vào `develop`.
  
![alt text](image.png)

Sau mỗi tuần, team sẽ merge `develop` vào `master` để release phiên bản mới.

## 2. Giới Thiệu Dự Án
**Mô tả :** 
 - **Tên Sản Phẩm:** DEVERS
- **Thể loại:** Website mạng xã hội dành cho cộng đồng lập trình viên
- **Điểm chính:** Tính năng, công nghệ
- **Công nghệ sử dụng:** Front-end: html,css,js - Back-end: Java Spring Boot - Database: MySQL
**Hướng dẫn sử dụng:** Clone repo vể sau đó chạy class Main
## 3. Các Chức Năng Chính

- **Chức năng 1:** Đăng ký, đăng nhập (Tích hợp xác thực qua google, facebook, github)
- **Chức năng 2:** Chọn các chủ đề quan tâm (Các bài viết sẽ liên quan đến chủ đề đã chọn)
- **Chức năng 3:** Đăng bài viết hỏi hoặc chia sẻ kiến thức về công nghệ
- **Chức năng 4:** Tìm kiếm các bài viết
- **Chức năng 5:** Nhắn tin thời gian thực - Có thể nhắn trực tiếp với người khác để hỏi trực tiếp
- **Chức năng 6:** Tích hợp chatbot - Hỏi và tổng hợp các kiến thức đã được chia sẻ trong nền tảng
- **Chức năng 7:** Xem profile của các người dùng khác và danh sách bài viết họ đã đăng
- **Chức năng 8:** Bình luận và đánh giá bài viết qua hệ thống upvote-downvote
## 4. Công Nghệ
#### 4.1. Công Nghệ Sử Dụng
- Html,css,js
- Java Spring Boot
- WebSocket
- MySQL
#### 4.2. Cấu Trúc Dự Án
**Sử dụng mô hình MVC, cấu trúc project như sau:** 
```
- main
  - java
    - com.example.demo
      - controller
      - custom_annotation
      - dto
      - exception_handle
      - model
      - repository
      - security
      - service
      - socket
      - util
      - validator
      - MyApplication
  - resources
    - static
      - images
      - js
      - styles
      - uploads
    - templates
      - All the html file here
```

Diễn giải:

- **java:** Chứa các package phần Controller và Model
- **resources:** Chứa các file để render view

Luồng hoạt động cơ bản của project
![image](https://github.com/user-attachments/assets/07ff1090-8a18-4141-93d1-d93eb02d1327)

...

## 5. Ảnh Demo

**Ảnh Demo:**
![image](https://github.com/user-attachments/assets/67b67d72-7359-4b5d-b299-975b46993aed)
![image](https://github.com/user-attachments/assets/92fcfc00-a41c-48bf-b19d-9482b13953b8)
![image](https://github.com/user-attachments/assets/2b52b9ca-915f-41a1-9b54-77da724f3726)
![image](https://github.com/user-attachments/assets/b7897cc3-a487-4370-a6e5-f91c49e164be)
![image](https://github.com/user-attachments/assets/3e5450a9-0182-47b8-89e5-c9987ed9fe6a)
![image](https://github.com/user-attachments/assets/c37457f7-b50d-4d17-b1df-51a0e46e1357)
![image](https://github.com/user-attachments/assets/0db5e70d-5e37-49df-8632-1aaa0b3046bb)


## 6. Các Vấn Đề Gặp Phải

#### Vấn Đề 1: Tìm kiếm bài viết bị chậm do làm bằng HTTP
- Do sử dụng HTTP nên khi tìm kiếm phải gõ đủ yêu cầu tìm kiếm sau đó ấn tìm kiếm thì nó mới hiện, gây ảnh hưởng đến trải nghiệm người dùng

#### Hành Động Giải Quyết 
**Giải pháp:** Đổi qua sử dụng websocket để tìm kiếm

#### Kết Quả
- Hợp lý với yêu cầu dự án, tăng trải nghiệm người dùng


#### Vấn Đề 2: Chuyển đổi từ CSR sang SSR 
- Do nhu cầu cần bảo mật API nên phải chuyển từ CSR sang SSR, dẫn đến cách triển khai bảo mật JWT như cũ bị khó triển khai

#### Hành Động Giải Quyết 
**Giải pháp:** Đổi qua bảo mật bằng session

#### Kết Quả
- Hợp lý với yêu cầu dự án, hơn nữa phù hợp để quản lý trao đổi thông tin qua websocket



