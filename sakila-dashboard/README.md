# ClassicModels Analytics Dashboard 📊

## 📸 Hình ảnh Thực tế (Screenshots)
![Dashboard Overview 1](image/Screenshot%20from%202026-04-15%2008-59-00.png)
![Dashboard Overview 2](image/Screenshot%20from%202026-04-15%2008-33-17.png)
![Analytics & Search 1](image/Screenshot%20from%202026-04-15%2008-35-24.png)
![Analytics & Search 2](image/Screenshot%20from%202026-04-15%2008-35-47.png)
![Analytics & Search 3](image/Screenshot%20from%202026-04-15%2008-36-55.png)
![AI Chatbot](image/Screenshot%20from%202026-04-15%2009-08-41.png)

ClassicModels Analytics Dashboard là một ứng dụng Web Full-Stack cung cấp cái nhìn tổng quan và phân tích dữ liệu trực quan từ cơ sở dữ liệu `classicmodels`. Ứng dụng này cung cấp các tính năng từ tra cứu thông tin (Search Explorer), báo cáo doanh thu, công nợ (Analytics), cho đến trợ lý ảo AI (Chatbot).

## ✨ Tính năng nổi bật

1. **Giao diện hiện đại (Modern UI):**
   - Thiết kế theo phong cách Dark Mode và Glassmorphism (hiệu ứng kính mờ).
   - Biểu đồ tương tác thông minh sử dụng **Chart.js**.
2. **Khám phá Dữ liệu (Search Explorer):**
   - Tìm kiếm Khách hàng (Customers) theo tên.
   - Tìm kiếm Sản phẩm (Products) theo tên.
   - Tìm kiếm Đơn hàng (Orders) theo khoảng thời gian báo cáo.
3. **Thống kê & Phân tích (Analytics):**
   - Biểu đồ đường (Line Chart) theo dõi Doanh thu theo tháng.
   - Biểu đồ cột (Bar Chart) vinh danh Top Khách hàng mang lại doanh thu cao nhất.
   - Biểu đồ cột (Bar Chart) danh sách Top 10 Khách hàng nợ đọng nhiều nhất (Highest Debtors).
   - Bảng Pivot Table hiển thị doanh thu đóng góp theo từng sản phẩm.
4. **Trợ lý Ảo AI (AI Assistant):**
   - Chatbot tích hợp trực tiếp trên màn hình, cấu hình gọi đến mô hình LLM từ xa (`openai/gpt-oss-20b`) thông qua Groq API, giúp người dùng giải đáp các thắc mắc về hệ thống.

## 🛠️ Công nghệ sử dụng

- **Frontend:** Vanilla HTML5, CSS3, JavaScript. Tích hợp thư viện Chart.js qua CDN.
- **Backend:** Java 17, Spring Boot 3.x, Spring Data JPA, RESTful API.
- **Database:** MySQL 8.0 (Được đóng gói tự động qua Docker).
- **AI Integrations:** Giao tiếp qua endpoint chat tích hợp Groq/OpenAI.

## 🚀 Hướng dẫn Cài đặt & Chạy ứng dụng

### BƯỚC 1: Khởi tạo Cơ sở dữ liệu (Database)
Đảm bảo máy bạn đã cài đặt **Docker** và **Docker Compose**.
Mở terminal tại thư mục gốc và chạy tập lệnh khởi tạo CSDL:
```bash
bash setup-db.sh
```
Tập lệnh này sẽ tự động tải file CSDL `classicmodels` và chạy Docker Compose thiết lập server MySQL ở port `3306`.

### BƯỚC 2: Khởi động Backend (Spring Boot)
Ứng dụng Backend Spring Boot sẽ nhận nhiệm vụ xử lý API và giao tiếp DB/AI. 
Mở một terminal mới, chuyển vào thư mục `backend` và chạy ứng dụng:
```bash
cd backend
export GROQ_API_KEY="your_groq_api_key_here"  # Nhập API Key của bạn nếu muốn dùng tính năng AI Chatbot
mvn spring-boot:run
```
*(Nếu bạn dùng Windows PowerShell, hãy thay thế `export` bằng `$env:GROQ_API_KEY="your_key"`)*

Backend sẽ chạy tại: `http://localhost:8080/`

### BƯỚC 3: Trải nghiệm Giao diện (Frontend)
Chỉ cần mở trực tiếp file HTML thông qua trình duyệt web của bạn, do API đã được cài đặt cấu hình CORS.
- Double-click (hoặc mở bằng trình duyệt/Live Server) file: `frontend/index.html`

---
*Dự án phục vụ môn học Hệ quản trị cơ sở dữ liệu (DBMS) / Database Programming.*
