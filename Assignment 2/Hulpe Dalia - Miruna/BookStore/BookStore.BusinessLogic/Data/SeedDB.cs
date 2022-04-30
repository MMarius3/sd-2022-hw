using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BookStore.BusinessLogic.Constants;
using BookStore.Repository.Data;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;

namespace BookStore.BusinessLogic.Data
{
    public static class SeedDB

    {
        public async static Task Initialize(ApplicationDbContext context, RoleManager<IdentityRole> roleManager)
        {
            context.Database.EnsureCreated();

            await CreateRolesAsync(roleManager, context);
        }

        #region Private
        

        private async static Task CreateRolesAsync(RoleManager<IdentityRole> roleManager, ApplicationDbContext context)

        {
            var isEmployeeRoleExistent = await roleManager.RoleExistsAsync(RoleConstants.Employee);

            var isAdminRoleExistent = await roleManager.RoleExistsAsync(RoleConstants.Administrator);


            if (!isEmployeeRoleExistent)
            {
                await roleManager.CreateAsync(new IdentityRole(RoleConstants.Employee));
            }

            if (!isAdminRoleExistent)
            {
                await roleManager.CreateAsync(new IdentityRole(RoleConstants.Administrator));
            }

            context.SaveChanges();
        }
        #endregion

    }
}
