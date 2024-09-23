# Thiết Kế Cơ Sở Dữ Liệu Mở Rộng cho Trang Web Mạng Xã Hội

## 1. Bảng `users`
- **Mục đích:** Lưu trữ thông tin người dùng.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| id           | integer      | Khóa chính, tự động tăng                   |
| username     | varchar      | Tên người dùng                             |
| email        | varchar      | Địa chỉ email người dùng                   |
| password_hash | varchar     | Mật khẩu đã băm                            |
| role         | varchar      | Vai trò (user, admin, v.v.)                |
| created_at   | timestamp    | Thời điểm tạo tài khoản                    |

## 2. Bảng `posts`
- **Mục đích:** Lưu trữ bài viết của người dùng.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| id           | integer      | Khóa chính, tự động tăng                   |
| title        | varchar      | Tiêu đề bài viết                           |
| body         | text         | Nội dung bài viết                          |
| user_id      | integer      | Khóa ngoại tham chiếu đến người dùng (users.id) |
| status       | varchar      | Trạng thái bài viết (draft, published)     |
| created_at   | timestamp    | Thời điểm tạo bài viết                     |

## 3. Bảng `follows`
- **Mục đích:** Lưu trữ mối quan hệ theo dõi giữa người dùng.

| Tên cột             | Kiểu dữ liệu | Mô tả                                       |
|---------------------|--------------|---------------------------------------------|
| following_user_id   | integer      | Khóa ngoại tham chiếu đến người dùng theo dõi (users.id) |
| followed_user_id    | integer      | Khóa ngoại tham chiếu đến người dùng được theo dõi (users.id) |
| created_at          | timestamp    | Thời điểm theo dõi                         |

## 4. Bảng `comments`
- **Mục đích:** Lưu trữ bình luận cho các bài viết.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| id           | integer      | Khóa chính, tự động tăng                   |
| post_id      | integer      | Khóa ngoại tham chiếu đến bài viết (posts.id) |
| user_id      | integer      | Khóa ngoại tham chiếu đến người dùng (users.id) |
| body         | text         | Nội dung bình luận                         |
| created_at   | timestamp    | Thời điểm tạo bình luận                    |

## 5. Bảng `votes`
- **Mục đích:** Lưu trữ thông tin về đánh giá cho bài viết và bình luận.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| id           | integer      | Khóa chính, tự động tăng                   |
| user_id      | integer      | Khóa ngoại tham chiếu đến người dùng (users.id) |
| post_id      | integer      | Khóa ngoại tham chiếu đến bài viết (posts.id, nullable) |
| comment_id   | integer      | Khóa ngoại tham chiếu đến bình luận (comments.id, nullable) |
| vote_type    | varchar      | Loại đánh giá (upvote, downvote)          |
| created_at   | timestamp    | Thời điểm tạo đánh giá                     |

## 6. Bảng `tags`
- **Mục đích:** Lưu trữ thông tin về thẻ cho các bài viết.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| id           | integer      | Khóa chính, tự động tăng                   |
| name         | varchar      | Tên thẻ                                    |
| created_at   | timestamp    | Thời điểm tạo thẻ                          |

## 7. Bảng `post_tags`
- **Mục đích:** Liên kết giữa bài viết và thẻ (many-to-many).

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| post_id      | integer      | Khóa ngoại tham chiếu đến bài viết (posts.id) |
| tag_id       | integer      | Khóa ngoại tham chiếu đến thẻ (tags.id)  |

## 8. Bảng `notifications`
- **Mục đích:** Lưu trữ thông báo cho người dùng.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| id           | integer      | Khóa chính, tự động tăng                   |
| user_id      | integer      | Khóa ngoại tham chiếu đến người dùng (users.id) |
| message      | varchar      | Nội dung thông báo                        |
| read_status  | boolean      | Trạng thái đã đọc (true, false)          |
| created_at   | timestamp    | Thời điểm tạo thông báo                    |

## 9. Bảng `user_settings`
- **Mục đích:** Lưu trữ cài đặt của người dùng.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| user_id      | integer      | Khóa ngoại tham chiếu đến người dùng (users.id) |
| setting_key   | varchar      | Tên cài đặt                                 |
| setting_value | varchar      | Giá trị cài đặt                            |

## 10. Bảng `reports`
- **Mục đích:** Lưu trữ thông tin về các báo cáo vi phạm.

| Tên cột      | Kiểu dữ liệu | Mô tả                                       |
|--------------|--------------|---------------------------------------------|
| id           | integer      | Khóa chính, tự động tăng                   |
| user_id      | integer      | Khóa ngoại tham chiếu đến người dùng (users.id) |
| post_id      | integer      | Khóa ngoại tham chiếu đến bài viết (posts.id, nullable) |
| comment_id   | integer      | Khóa ngoại tham chiếu đến bình luận (comments.id, nullable) |
| reason       | text         | Lý do báo cáo                              |
| created_at   | timestamp    | Thời điểm tạo báo cáo                     |

# Mối Quan Hệ Giữa Các Bảng
- **Giữa `users`, `posts`, `comments`, `votes`, và `follows`:**
  - Một người dùng có thể có nhiều bài viết và bình luận.
  - Một bài viết có thể có nhiều bình luận.
  - Một người dùng có thể theo dõi nhiều người dùng khác.
  - Một người dùng có thể đánh giá nhiều bài viết và bình luận.

- **Giữa `posts` và `tags`:**
  - Một bài viết có thể có nhiều thẻ (many-to-many).
  - Một thẻ có thể liên kết với nhiều bài viết.

- **Giữa `users` và `notifications`:**
  - Một người dùng có thể có nhiều thông báo.

- **Giữa `users` và `user_settings`:**
  - Một người dùng có thể có nhiều cài đặt.

- **Giữa `reports`, `users`, `posts`, và `comments`:**
  - Một báo cáo có thể liên quan đến một người dùng, một bài viết hoặc một bình luận.


# Thiết Kế Cơ Sở Dữ Liệu cho Trang Web Mạng Xã Hội

Table users {

  id integer [primary key]

  username varchar

  email varchar

  password_hash varchar

  role varchar

  created_at timestamp

}


Table posts {

  id integer [primary key]
  
  title varchar
  
  body text [note: 'Content of the post']
  
  user_id integer
  
  status varchar
  
  created_at timestamp

}

Table follows {

  following_user_id integer
  
  followed_user_id integer
  
  created_at timestamp 

}

Table comments {

  id integer [primary key]
  
  post_id integer
  
  user_id integer
  
  body text
  
  created_at timestamp

}

Table votes {

  id integer [primary key]
  
  user_id integer
  
  post_id integer
  
  comment_id integer 
  
  vote_type varchar
  
  created_at timestamp

}

Table tags {

  id integer [primary key]
  
  name varchar
  
  created_at timestamp

}

Table post_tags {

  post_id integer
  
  tag_id integer

}

Table notifications {

  id integer [primary key]
  
  user_id integer
  
  message varchar
  
  read_status boolean
  
  created_at timestamp

}


Table user_settings {

  user_id integer
  
  setting_key varchar
  
  setting_value varchar

}

Table reports {

  id integer [primary key]
  
  user_id integer
  
  post_id integer 
  
  comment_id integer 
  
  reason text
  
  created_at timestamp

}

# Relationships

Ref: posts.user_id > users.id

Ref: follows.following_user_id < users.id 

Ref: follows.followed_user_id < users.id 

Ref: comments.post_id > posts.id 

Ref: comments.user_id > users.id 

Ref: votes.user_id > users.id 

Ref: votes.post_id > posts.id 

Ref: votes.comment_id > comments.id 

Ref: post_tags.post_id > posts.id 

Ref: post_tags.tag_id > tags.id 

Ref: notifications.user_id > users.id 

Ref: user_settings.user_id > users.id 

Ref: reports.user_id > users.id 

Ref: reports.post_id > posts.id 

Ref: reports.comment_id > comments.id 

