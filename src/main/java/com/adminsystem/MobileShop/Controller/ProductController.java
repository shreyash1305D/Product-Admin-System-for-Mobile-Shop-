package com.adminsystem.MobileShop.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adminsystem.MobileShop.Services.ProductService;
import com.adminsystem.MobileShop.entities.Product;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final ProductService serv;

	public ProductController(ProductService serv) {
		this.serv = serv;
	}

	@GetMapping("/add")
	public String addProduct(Model m) {
		m.addAttribute("product", new Product());
		return "addProduct";
	}
	@PostMapping("/add")
	public String add(@Validated @ModelAttribute Product product, BindingResult result, Model model) {
	    System.out.println("Received modelName: " + product.getModelName());

	    if (result.hasErrors()) {
	        model.addAttribute("product", product);
	        return "addProduct";
	    }

	    serv.save(product);
	    return "redirect:/dashboard";
	}
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", serv.getAll());
        model.addAttribute("lowStock", serv.lowStock());
        return "productList";
    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> results = serv.search(keyword);
        model.addAttribute("products", results);
        model.addAttribute("lowStock", serv.lowStock());
        model.addAttribute("brands", serv.search(keyword));
        return "productList"; // Make sure this matches your HTML filename
    }
    @GetMapping("/filter")
    public String filterProducts(@RequestParam(required = false) Double minPrice,
                                 @RequestParam(required = false) Double maxPrice,
                                 @RequestParam(required = false) String brand,
                                 Model model) {
        List<Product> filtered = serv.filterByPriceAndBrand(minPrice, maxPrice, brand);
        model.addAttribute("products", filtered);
        model.addAttribute("lowStock", serv.lowStock());
        model.addAttribute("brands", serv.filterByPriceAndBrand(minPrice, maxPrice, brand));
        return "productList"; // same view name as your HTML
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("product", serv.getById(id));
        return "editProduct";
    }
    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id, @Validated @ModelAttribute Product product, BindingResult result) {
        if (result.hasErrors()) return "editProduct";
        product.setProdid(id);
        serv.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        serv.delete(id);
        return "redirect:/products";
    }


}
