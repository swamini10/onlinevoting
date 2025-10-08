<html>
<head>
    <style>
        body { font-family: Arial, sans-serif; background: #f9f9f9; color: #222; }
        .container {
            background: #fff;
            padding: 24px;
            border-radius: 8px;
            max-width: 500px;
            margin: 40px 0 40px 40px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        .header { font-size: 22px; font-weight: bold; margin-bottom: 16px; color: #2a7ae2; }
        .success-box {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            padding: 15px;
            border-radius: 5px;
            margin: 16px 0;
        }
        .success-title {
            font-weight: bold;
            color: #155724;
            margin-bottom: 8px;
            font-size: 16px;
        }
        .login-button {
            background: #2a7ae2;
            color: white;
            padding: 12px 24px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin: 16px 0;
            font-weight: bold;
        }
        .features-list {
            margin: 20px 0;
            padding-left: 20px;
        }
        .features-list li {
            margin: 8px 0;
            color: #555;
        }
        .footer { margin-top: 32px; font-size: 13px; color: #888; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">🎉 Account Successfully Activated!</div>
        <p>Hello <b>${name}</b>,</p>
        <p>Great news! Your account has been successfully verified and approved by our voting team.</p>
        
        <div class="success-box">
            <div class="success-title">✅ Your Account is Now Active</div>
            <p>You can now access all features of the Online Voting System.</p>
        </div>
        
        <p><strong>What you can do now:</strong></p>
        <ul class="features-list">
            <li>Login to your account using your registered email</li>
            <li>Participate in ongoing elections and polls</li>
            <li>View election results and statistics</li>
            <li>Update your profile information</li>
            <li>Access your voting history</li>
        </ul>
        
        <a href="${loginUrl}" class="login-button">Login to Your Account</a>
        
        <p><strong>Your Account Details:</strong></p>
        <ul>
            <li><strong>Email:</strong> ${emailId}</li>
            <li><strong>Registration Date:</strong> ${registrationDate}</li>
            <li><strong>Approval Date:</strong> ${approvalDate}</li>
        </ul>
        
        <p>If you have any questions or need assistance, please don't hesitate to contact our support team.</p>
        
        <p>Thank you for joining the Online Voting System!</p>
        
        <div class="footer">
            Regards,<br/>
            Online Voting Team<br/>
            <small>This is an automated message. Please do not reply to this email.</small>
        </div>
    </div>
</body>
</html>