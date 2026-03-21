select distinct o1_0.id,o1_0.created_at,oi2_0.order_id,oi2_0.id,oi2_0.price,oi2_0.product_id,p2_0.id,p2_0.category_id,p2_0.company_id,p2_0.created_at,p2_0.description,p2_0.is_active,p2_0.name,p2_0.price,p2_0.stock,p2_0.updated_at,oi2_0.quantity,o1_0.status,o1_0.total_amount,o1_0.updated_at,o1_0.user_id,u1_0.id,u1_0.company_id,u1_0.created_at,u1_0.email,u1_0.name,u1_0.password,u1_0.role,u1_0.updated_at
from customer_order o1_0 left join order_item oi1_0 on o1_0.id=oi1_0.order_id
    left join product p1_0 on p1_0.id=oi1_0.product_id
    left join company c1_0 on c1_0.id=p1_0.company_id
    left join order_item oi2_0 on o1_0.id=oi2_0.order_id
    left join product p2_0 on p2_0.id=oi2_0.product_id
    join app_user u1_0 on u1_0.id=o1_0.user_id where o1_0.status=? and c1_0.id=?