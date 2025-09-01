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
        .otp-box {
            font-size: 28px;
            font-weight: bold;
            color: #e67e22;
            background: #f6e9d7;
            padding: 12px 24px;
            border-radius: 6px;
            display: inline-block;
            margin: 16px 0;
            letter-spacing: 4px;
        }
        .footer { margin-top: 32px; font-size: 13px; color: #888; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">OTP Verification</div>
        <p>Hello <b>${name}</b>,</p>
        <p>Your One Time Password (OTP) for Online Voting System is:</p>
        <div>${otp}</div>
        <p>This OTP is valid for the next 10 minutes. Please do not share it with anyone.</p>
        <div class="footer">
            Regards,<br/>
            Online Voting Team
        </div>
    </div>
</body>
</html>