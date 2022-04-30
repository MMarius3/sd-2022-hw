using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BookStore.Models
{
    public class UserViewModel
    {
        [Required(ErrorMessage = "Email required")]
        [EmailAddress(ErrorMessage = "Invalid Email Address")]
        public string Email { get; set; }

        [Display(Name = "User name")]
        [Required(ErrorMessage = "User name required")]
        public string UserName { get; set; }

        [Required(ErrorMessage = "Password required")]
        public string Password { get; set; }
    }
}
