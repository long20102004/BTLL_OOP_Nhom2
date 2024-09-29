
Table users {
  id int [pk, increment]
  username varchar [not null, unique]
  email varchar [not null, unique]
  password varchar [not null]
  created_at timestamp [default: `current_timestamp`]
}


Table posts {
  id int [pk, increment]
  user_id int [not null, ref: > users.id]
  content text [not null]
  created_at timestamp
  tag varchar
}

Table comments{
  id int [pk, increment]
  user_id int [not null, ref: > users.id]
  post_id int [not null, ref: > posts.id]
  content text
  created_at timestamp
}

Table messages{
  id int [pk, increment]
  sender_id int [not null, ref : > users.id]
  receiver_id int [not null, ref: > users.id]
  content binary
  time_send timestamp
}
Table comment_votes{
  id int [pk, increment]
  comment_id int [not null, ref: > comments.id]
  voter_id int [not null, ref: > users.id]
  created_at timestamp
}
Table post_votes{
  id int [pk, increment]
  user_id int [not null, ref: > users.id]
  post_id int [not null, ref: > posts.id]
  created_at timestamp
}

Table follows{
  id int [pk, increment]
  follower_id int [not null, ref: > users.id]
  following_id int [not null, ref: > users.id]
  created_at timestamp
}
Table notifications{
  id int [pk, increment]
  content varchar
  receiver_id int [not null, ref: > users.id]
  send_time timestamp
}

// Ref: posts.user_id > users.id
// Ref: comments.user_id > users.id
// Ref: comments.post_id > posts.id
// Ref: messages.sender_id > users.id
// Ref: messages.receiver_id > users.id
// Ref: comment_votes.comment_id > comments.id
// Ref: comment_votes.voter_id > users.id
// Ref: post_votes.user_id > users.id
// Ref: post_votes.post_id > posts.id
// Ref: follows.follower_id > users.id
// Ref: follows.following_id > users.id
// Ref: notifications.receiver_id > users.id


