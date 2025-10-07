<html>
<head>
    <style>
        body { font-family: Arial, sans-serif; background: #f9f9f9; color: #222; }
        .container {
            background: #fff;
            padding: 24px;
            border-radius: 8px;
            max-width: 500px;
            margin: 40px 0 40px 40px; /* Top, Right, Bottom, Left */
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        .header { font-size: 22px; font-weight: bold; margin-bottom: 16px; color: #2a7ae2; }
        .status-box {
            background: #fff3cd;
            border: 1px solid #ffeaa7;
            padding: 15px;
            border-radius: 5px;
            margin: 16px 0;
        }
        .status-title {
            font-weight: bold;
            color: #856404;
            margin-bottom: 8px;
        }
        .process-steps {
            margin: 20px 0;
            padding-left: 20px;
        }
        .process-steps li {
            margin: 8px 0;
            color: #555;
        }
        .footer { margin-top: 32px; font-size: 13px; color: #888; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">Registration Submitted Successfully!</div>
        <p>Hello <b>${name}</b>,</p>
        <p>Thank you for registering with the Online Voting System. Your account has been created successfully.</p>
        
        <div class="status-box">
            <div class="status-title">Current Status: Pending Verification</div>
            <p>Your account is currently inactive and under review.</p>
        </div>
        
        <p><strong>Next Steps:</strong></p>
        <ol class="process-steps">
            <li>Document Verification - Our team will verify your submitted documents</li>
            <li>Officer Approval - A designated officer will review and approve your application</li>
            <li>Account Activation - Your account will be activated within 24 hours after approval</li>
        </ol>
        
        <p><strong>Expected Timeline:</strong> Your account will be activated within 24 hours after successful document verification and officer approval.</p>
        
        <p>You will receive an email notification once your account is activated and ready to use.</p>
        
        <p>If you have any questions, please contact our support team.</p>
        
        <div class="footer">
            Regards,<br/>
            Online Voting Team<br/>
            <small>This is an automated message. Please do not reply to this email.</small>
        </div>
    </div>
</body>
</html>