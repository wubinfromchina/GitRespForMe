package com.wb.reggie.DTO;

import com.wb.reggie.pojo.Setmeal;
import com.wb.reggie.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
