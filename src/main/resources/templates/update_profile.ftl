<html>
<head>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            background: #f9f9f9; 
            color: #222; 
            margin: 0;
            padding: 0;
        }
        .container {
            background: #fff;
            padding: 24px;
            border-radius: 8px;
            max-width: 500px;
            margin: 40px auto;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        .header { 
            font-size: 22px; 
            font-weight: bold; 
            margin-bottom: 16px; 
            color: #2a7ae2; 
            text-align: center;
        }
        .content {
            line-height: 1.6;
            margin-bottom: 20px;
        }
        .profile-details {
            background: #f8f9fa;
            padding: 16px;
            border-radius: 6px;
            margin: 16px 0;
            border-left: 4px solid #2a7ae2;
        }
        .detail-row {
            margin: 8px 0;
            display: flex;
            justify-content: space-between;
        }
        .detail-label {
            font-weight: bold;
            color: #555;
        }
        .detail-value {
            color: #333;
        }
        .warning {
            background: #fff3cd;
            color: #856404;
            padding: 12px;
            border-radius: 6px;
            border: 1px solid #ffeaa7;
            margin: 16px 0;
        }
        .footer { 
            margin-top: 32px; 
            font-size: 13px; 
            color: #888; 
            text-align: center;
            border-top: 1px solid #eee;
            padding-top: 16px;
        }
        .button {
            display: inline-block;
            padding: 12px 24px;
            background: #2a7ae2;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            margin: 16px 0;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            Profile Update Notification
        </div>
        
        <div class="content">
            <p>Hello <b>${name!"User"}</b>,</p>
            
            <p>Your profile has been successfully updated. Below are the details of the changes made to your account:</p>
            
            <div class="profile-details">
                <h3 style="margin-top: 0; color: #2a7ae2;">Updated Information</h3>
                <#if email??>
                    <div class="detail-row">
                        <span class="detail-label">Email:</span>
                        <span class="detail-value">${email}</span>
                    </div>
                </#if>
                <#if phone??>
                    <div class="detail-row">
                        <span class="detail-label">Phone:</span>
                        <span class="detail-value">${phone}</span>
                    </div>
                </#if>
                <#if address??>
                    <div class="detail-row">
                        <span class="detail-label">Address:</span>
                        <span class="detail-value">${address}</span>
                    </div>
                </#if>
                <#if dateOfBirth??>
                    <div class="detail-row">
                        <span class="detail-label">Date of Birth:</span>
                        <span class="detail-value">${dateOfBirth}</span>
                    </div>
                </#if>
                <div class="detail-row">
                    <span class="detail-label">Last Updated:</span>
                    <span class="detail-value">${updateDate!"Today"}</span>
                </div>
            </div>
            
            <div class="warning">
                <strong>Security Notice:</strong> If you did not make these changes or suspect unauthorized access to your account, please contact our support team immediately or reset your password.
            </div>
            
            <#if loginUrl??>
                <p style="text-align: center;">
                    <a href="${loginUrl}" class="button">Login to Your Account</a>
                </p>
            </#if>
            
            <p>Thank you for keeping your profile information up to date. This helps us provide you with better service and ensures the security of your account.</p>
        </div>
        
        <div class="footer">
            <p>
                Best regards,<br/>
                <strong>Online Voting System Team</strong>
            </p>
            <p>
                This is an automated message. Please do not reply to this email.<br/>
                For support, contact us at <a href="mailto:support@onlinevoting.com">support@onlinevoting.com</a>
            </p>
        </div>
    </div>
</body>
</html>