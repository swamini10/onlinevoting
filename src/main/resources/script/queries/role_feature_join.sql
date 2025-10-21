-- Get all features for a specific role with feature details
SELECT 
    rfm.role_id,
    rfm.feature_id,
    f.name AS feature_name,
    f.description,
    f.menu_name,
    f.url,
    f.icon,
    f.is_active AS feature_active,
    rfm.is_active AS mapping_active
FROM role_feature_mapping rfm
INNER JOIN feature f ON rfm.feature_id = f.id
WHERE rfm.role_id = 1
AND rfm.is_active = 1
AND f.is_active = 1
ORDER BY f.menu_name, f.name;

-- Alternative query for multiple roles
SELECT 
    rfm.role_id,
    f.id AS feature_id,
    f.name AS feature_name,
    f.menu_name,
    f.url,
    f.icon
FROM role_feature_mapping rfm
INNER JOIN feature f ON rfm.feature_id = f.id
WHERE rfm.role_id IN (1, 2, 3)
AND rfm.is_active = 1
ORDER BY rfm.role_id, f.menu_name, f.name;
